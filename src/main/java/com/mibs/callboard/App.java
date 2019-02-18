/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.mibs.callboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.lang.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.mibs.asterisk.Action;
import com.mibs.asterisk.ActionLogOff;
import com.mibs.asterisk.ActionLogin;
import com.mibs.asterisk.ActionQueueShow;
import com.mibs.asterisk.AuthenticationFailedException;
import com.mibs.asterisk.CurrentQueue;
import com.mibs.asterisk.QueueContents;
import com.mibs.asterisk.Utils;
import com.mibs.asterisk.events.AgentCalledEvent;
import com.mibs.asterisk.events.AsteriskEvent;
import com.mibs.asterisk.events.BridgeEvent;
import com.mibs.asterisk.events.QueueMemberAddedEvent;
import com.mibs.asterisk.events.QueueMemberRemovedEvent;
import com.mibs.asterisk.events.QueueMemberStatusEvent;
import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class App extends JFrame{
	private Group parallelGroup;
	private PanelWrapper panelWrapper;
	
	private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
	private static final org.apache.logging.log4j.Logger logger =  LogManager.getLogger(App.class.getName());
	public static final String CONFIG_NAME = "application.properties";
	public static final int backlog = 10;

	private Socket socket;
	private ExecutorService service = null;
	private BufferedReader reader;
	private AsteriskMessageListener asteriskMessageListener;
	
	class PanelWrapper implements Wrapper{
		private Map<String, BoardPanel> panels;
		
		public PanelWrapper() {
			
			panels = new TreeMap<>();
		}
		
		private Optional<String> getAgentId(Long queueid, Long peerid  ) {
			
			String result = null;
			try (java.sql.Connection connect = DriverManager.getConnection(Utils.dsURL(), Utils.dbuser, Utils.dbpassword);
					Statement statement = connect.createStatement()) {
				String sql = "SELECT name  FROM agents ag  inner join " + 
				" (select agentid  from members where queueid=" + queueid + " and peerid=" + peerid + "  and event='ADDMEMBER' ORDER BY ID DESC LIMIT 1) mb " +  
				" on ag.id = mb.agentid" ;
			
				ResultSet rs = statement.executeQuery(sql);
				rs.next();
				result = rs.getString("name");
				rs.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
				
			}
			return (result != null) ? Optional.of(result) : Optional.empty();
		}
		
		private Optional<Long> getPeerIdByName(String name) {
			
			Long result = null;
			try (Connection connect = DriverManager.getConnection(Utils.dsURL(), Utils.dbuser, Utils.dbpassword);
					Statement statement = connect.createStatement()) {
				String sql = "select id from peers where name = '" + name.toUpperCase() + "'";
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next())  result = rs.getLong("id");	
				rs.close();
			} catch (Exception e) {
				logger.error("Error in getPeerIdByNam :" +  e.getMessage());
			}
			return (result != null) ? Optional.of(result) : Optional.empty();
		}

		private Optional<Long> getQueueIdByName(String name) {
			Long result = null;
			try (Connection connect = DriverManager.getConnection(Utils.dsURL(), Utils.dbuser, Utils.dbpassword);
				 Statement statement = connect.createStatement()) {
				String sql = "select id from queues where name = '" + name + "'";
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) result = rs.getLong("id");
				rs.close();
			} catch (Exception e) {
				logger.error("Error in getQueueIdByName " + e.getMessage());
			}
			return (result != null) ? Optional.of(result) : Optional.empty();
		}
	    public void drawQueuNames( List<String> queues ) {
	    	panels.clear();
	    	queues.forEach( queue->{
				panels.put( queue, new BoardPanel( queue, queues.size() ));
			});
	    }
		public Collection<BoardPanel> getAllBoardPanel(){
			return panels.values();
		}
		public void addAgent(String queue, String phone) {
			String name = "???";
			if ((queue != null && queue.length() > 0) & (phone != null && phone.length() > 0) ) {
				Optional<Long> oP = getPeerIdByName(phone);
				Optional<Long> oQ = getQueueIdByName(queue);
			
				if (oP.isPresent() && oQ.isPresent()) {
					Optional<String> aA = getAgentId(oQ.get(),oP.get());
					name = aA.get();
				}
				panels.get(queue).addAgent(phone, name);; 
			}
			else {
				logger.error("Error! Wrong values for queue/peer!");;
			}
		}
		public void removeAgent(String queue, String phone) {
			panels.get(queue).removeAgent(phone);
		}
		public void changeAgentStatus(String queue, String phone, int status ) {
			panels.get(queue).changeAgentStatus(phone, status);
		}
	}
	class AsteriskMessageListener {

		private Map<String, Class<? extends AsteriskEvent>> registeredEventClasses;

		public AsteriskMessageListener() throws UnknownHostException, IOException, AuthenticationFailedException {
			registeredEventClasses = new HashMap<>();
			registerEventClasses();

			List<String> queues = new ArrayList<>();
			QueueContents contents = null;
			socket = new Socket(Utils.getAsteriskHost(), Utils.getAsteriskPort());
			
			Action action = new ActionLogin(socket, Utils.getAsterisUser(), Utils.getAsterisPassword(), null, null);
			reader = action.getReader();

			Optional<Action> optQueueShow = action.getResponce();
			if (!optQueueShow.isPresent()) {
			
				System.exit(0);
			}
			ActionQueueShow actionQueueShow = (ActionQueueShow) optQueueShow.get();
			contents = actionQueueShow.getQueueContents();
			for (CurrentQueue currentQueue : contents.getQueues()) {
				queues.add(currentQueue.getQueue());
			}
			panelWrapper.drawQueuNames(queues);
			for (CurrentQueue currentQueue : contents.getQueues()) {
				for (String s : currentQueue.getMembers()) {
					panelWrapper.addAgent(currentQueue.getQueue(), s);
				}
			}
		}

		public void doListen() throws IOException, AuthenticationFailedException {
			StringBuilder sb = null;
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				
				//System.out.println(line);
				if (line.startsWith("Event")) {
					sb = new StringBuilder();
				}
				if (sb != null) {
					sb.append(line);
					sb.append("\n");
				}
				if (line.length() == 0) {
					Optional<AsteriskEvent> opt = buildEvent(sb);
					if (opt.isPresent()) {
						
						AsteriskEvent event = opt.get();
						event.execute(panelWrapper);
					}
					sb = null;
				}
			}
		}

		private void registerEventClasses() {
			registerEventClasses(QueueMemberAddedEvent.class);
			registerEventClasses(QueueMemberRemovedEvent.class);
			registerEventClasses(QueueMemberStatusEvent.class);
			registerEventClasses(AgentCalledEvent.class);
		}

		private void registerEventClasses(Class<? extends AsteriskEvent> clazz) {
			String className;
			String eventType;
			className = clazz.getName();
			eventType = className.substring(className.lastIndexOf('.') + 1).toLowerCase(Locale.ENGLISH);
			if (!eventType.endsWith("event")) {
				throw new IllegalArgumentException(clazz + " is not a AsteriskEvent");
			}
			eventType = eventType.substring(0, eventType.length() - "event".length());
			registeredEventClasses.put(eventType.toLowerCase(Locale.US), clazz);
		}

		private Optional<String> eventName(StringBuilder line) {
			String eventName = null;
			if (line != null && line.length() > 0) {
				eventName = line.toString().split("\n")[0].split(":")[1].toLowerCase().trim();
			}
			return (eventName != null) ? Optional.of(eventName) : Optional.empty();
		}

		public Optional<AsteriskEvent> buildEvent(StringBuilder line) {
			Class<? extends AsteriskEvent> eventClass = null;
			Constructor<?> constructor = null;
			AsteriskEvent event = null;
			Map<String, Method> methodMap = new HashMap<String, Method>();
			Map<String, String> cmdMap = new HashMap<String, String>();
			Optional<String> optEventName = eventName(line);
			if (!optEventName.isPresent())
				return Optional.empty();
			eventClass = registeredEventClasses.get(optEventName.get());
			if (eventClass == null)
				return Optional.empty();

			try {
				constructor = eventClass.getConstructor();
			} catch (NoSuchMethodException e1) {
				return Optional.empty();
			} catch (SecurityException e1) {
				return Optional.empty();
			}
			try {
				event = (AsteriskEvent) constructor.newInstance();
			} catch (InstantiationException e1) {
				return Optional.empty();
			} catch (IllegalAccessException e1) {
				return Optional.empty();
			} catch (IllegalArgumentException e1) {
				return Optional.empty();
			} catch (InvocationTargetException e1) {
				return Optional.empty();
			}
			Class<? extends AsteriskEvent> buildedClass = event.getClass();

			for (Method m : buildedClass.getMethods()) {
				if (m.getName().startsWith("set")) {
					methodMap.put(m.getName().toLowerCase(Locale.ENGLISH), m);
				}
			}
			String attr, value, s;
			Method method;
			String[] lines = line.toString().split("\n");
			for (int i = 0; i < lines.length; i++) {
				s = lines[i].toLowerCase();
				if (s.contains(":")) {
					String[] arr = s.split(":");
					attr = arr[0].replaceAll("\\s", "");
					attr = attr.indexOf("-") > 0 ? attr.replace("-", "") : attr;
					value = arr[1].replaceAll("\\s", "");
					cmdMap.put("set" + attr, value);
				}
			}
			for (Map.Entry<String, String> bmap : cmdMap.entrySet()) {
				if ((method = methodMap.get( bmap.getKey()) ) != null) {
					try {
						method.invoke(event, bmap.getValue());
					} catch (IllegalAccessException e) {
						return Optional.empty();
					} catch (IllegalArgumentException e) {
						return Optional.empty();
					} catch (InvocationTargetException e) {
						return Optional.empty();
					}
				}
			}

			return (event != null) ? Optional.of(event) : Optional.empty();
		}
	}

	private void init(String file) {
	
		Utils.initConfig(file);
		Utils.initRabbitMQ();	
		
	}

    public App() {
    	
    }
	public App( String file ) {
		super();
		init(file);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent event) {
				service = Executors.newSingleThreadExecutor();
				service.execute(() -> {
					try {
						try {
							asteriskMessageListener.doListen();

						} catch (AuthenticationFailedException e) {
							// TODO Auto-generated catch block
							logger.error("Error in Authentication,  time: " +  fmt.format(LocalDateTime.now()));
						}
					} catch (IOException e1) {
						logger.error("Error in IOException, code line 253  time: " +  fmt.format(LocalDateTime.now()));
					}
				});
			}

			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				Action logoff;
				if (service != null)
					service.shutdown();
				try {
					if (service.isTerminated()) {
						logoff = new ActionLogOff(socket, null);
						logoff.doCommand();
						
						System.exit(0);
					}
				} catch (IOException e) {
					logger.error("logoff with error: " + e.getMessage());
					System.exit(0);
				}

			}
		});

		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		panelWrapper = new PanelWrapper();

		try {
			asteriskMessageListener = new AsteriskMessageListener();

			GroupLayout layout = new GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			Group parallelGroup = layout.createParallelGroup();
			panelWrapper.getAllBoardPanel().forEach(panel -> {
				parallelGroup.addComponent(panel);
			});
			Group sequentialGroup = layout.createSequentialGroup();
			panelWrapper.getAllBoardPanel().forEach(panel -> {
				sequentialGroup.addComponent(panel);
			});
			layout.setHorizontalGroup(layout.createParallelGroup().addGroup(sequentialGroup));
			layout.setVerticalGroup(layout.createSequentialGroup().addGroup(parallelGroup));

			setTitle("Callboard");
			getContentPane().setBackground(Color.BLACK);
			//pack();
			//setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
			setExtendedState(JFrame.MAXIMIZED_BOTH); 
			setUndecorated(true);
			setResizable(true);
			setMaximumSize(DimMax);
			setExtendedState(MAXIMIZED_BOTH);

			GraphicsDevice device =getGraphicsConfiguration().getDevice();
		    boolean result = device.isFullScreenSupported();
		    device.setFullScreenWindow(this); 
			
		} catch (Exception e) {
			logger.error("Can't start callboard connection: " + e.getMessage());
			System.exit(0);

		}

	}
	public String getGreeting() {
		return "Hello world.";
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					if (args != null && args.length > 0 ) {
						App app = new App(args[0]);
						
						app.setVisible(true);
						logger.info("Asterisk Callboard is started at time:   " +  fmt.format(LocalDateTime.now()));
					}else {
						logger.error("Error! No configuration file provided!");
					}

				} catch (Exception ex) {
					logger.error("Error string Callboard, line code 438, time: "  + fmt.format(LocalDateTime.now()));
				}
				//app.handleAsterisk();

			}
		});

	}

}

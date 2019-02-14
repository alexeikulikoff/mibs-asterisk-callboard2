package com.mibs.asterisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mibs.callboard.App;



public class ActionQueueShow extends AbstractAction implements Action{
	
	private static final Logger logger = LoggerFactory.getLogger(ActionQueueShow.class.getName());
	private final Pattern pt = Pattern.compile("SIP/\\d+");
	
	public ActionQueueShow(Socket s,String queue, String peer) throws IOException {
		super(s, queue, peer);
	}
	@Override
	public void doCommand(String queue) throws IOException {
		writer.write("Action: COMMAND\r\nActionID:12345\r\ncommand: queue show " + queue + "\r\n\r\n");
		writer.flush();
	}
	
	public QueueContents getQueueContents() throws IOException {
		
		QueueContents content = new QueueContents();
		try (Connection connect = DriverManager.getConnection(Utils.dsURL(), Utils.dbuser, Utils.dbpassword);
				Statement statement = connect.createStatement()) {
			String sql = "select name from queues" ;
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String queue = rs.getString("name");
				CurrentQueue currentQueue =  new CurrentQueue( queue ) ;
				doCommand( queue );
				boolean memberFlag = false;
				for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					if (line.contains("Members") ){
						memberFlag = true;
					}
					if (memberFlag) {
						if (!line.contains("Members:") & !line.contains("No Members") & !line.contains("No Callers"))
						{	
						  Matcher m = pt.matcher(line);
						  if (m.find()) currentQueue.addMember( m.group(0) );
						}	
					}
					if (line.contains("Callers") & memberFlag){
						memberFlag = false;
						content.addQueueResponce(currentQueue);
					}
					if (line.contains("No Callers")) break;
				}
				
			}
			rs.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			
		}
		return content;
	}
	@Override
	public Optional<Action> getResponce() throws IOException, AuthenticationFailedException {
		
		return null;
	}
	@Override
	public BufferedReader getReader() {
		
		return reader;
	}
	@Override
	public void doCommand() throws IOException {
	}

}

package com.mibs.asterisk;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;


import redis.clients.jedis.Jedis;

public class Utils {

	public static String dbhost;
	public static  String dbname;
	public static  String dbuser;
	public static  String dbpassword;
	
	private static String rabbitmqHost;
	private static String rabbitmqUsername;
	private static String rabbitmqPpassword;
	
	private static String asterisUser;
	private static String asterisPassword;
	private static String asteriskHost;
	private static int 	  asteriskPort;
	
	private static String redisHost;
	private static String redisUser;
	private static String redisPassword;
	
	public static String getRedisPassword() {
		return redisPassword;
	}

	private static Channel channel;
	private static final Logger logger =  LoggerFactory.getLogger(Utils.class.getName());

	public static String dsURL( ) {
		return "jdbc:mysql://" + dbhost + ":3306/" + dbname + "?useUnicode=yes&characterEncoding=UTF-8";
	}
	
	public static void initConfig(String config) {
		Properties prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream( config );
			prop.load( fis );
			asterisUser = prop.getProperty("asterisk_user");
			asterisPassword = prop.getProperty("asterisk_password");
			asteriskHost = prop.getProperty("asterisk_host");
			asteriskPort = Integer.parseInt(prop.getProperty("asterisk_port"));
			
			rabbitmqHost = prop.getProperty("rabbitmq_host");
			rabbitmqUsername = prop.getProperty("rabbitmq_username");
			rabbitmqPpassword = prop.getProperty("rabbitmq_password");
					
			dbhost = prop.getProperty("dbhost");
			dbname = prop.getProperty("dbname");
			dbuser = prop.getProperty("dbuser");
			dbpassword = prop.getProperty("dbpassword");
			
			redisHost = prop.getProperty("redis_host");
			redisUser = prop.getProperty("redis_user");
			redisPassword = prop.getProperty("redis_pssword");
		
		} catch (Exception e1) {
			logger.debug("Configuration file: " + config + "  is not found!");
			System.exit(0);
		}
	}
    public static void publish(String  destcallerIdnum, String callerid) {
    	String queue = "SIP_" + destcallerIdnum;
    	
    	Jedis jedis = new Jedis(Utils.redisHost); 
		jedis.auth(Utils.getRedisPassword());
    	
    	byte[] res = jedis.get(callerid.trim().getBytes());
    	if ((res != null) && (res.length >  0)) {
    		try {
    			channel.basicPublish("", queue, null, res);
    		} catch (Exception e) {
    			logger.error("Error public message to RabbitMQ queue: " + queue + " for callid: " + callerid);
    		}
    	
    	}else {
    		Patient patient = new Patient("","","",callerid);
    		byte[] rc = SerializationUtils.serialize(patient);
    		try {
    			channel.basicPublish("", queue, null, rc);
    		} catch (Exception e) {
    			logger.error("Error public message to RabbitMQ queue: " + queue + " for callid: " + callerid);
    		}
    		logger.error("No data found for callerid: "  + callerid);
    	}
    	
    	jedis.close();
    }
	public static void initRabbitMQ() {
		ResultSet rs = null;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost( rabbitmqHost );
		factory.setUsername( rabbitmqUsername );
		factory.setPassword( rabbitmqPpassword );
		factory.setPort(5672);
		com.rabbitmq.client.Connection connection;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			try (Connection connect = DriverManager.getConnection(Utils.dsURL(), Utils.dbuser, Utils.dbpassword);
					Statement statement = connect.createStatement()) 
				{
					String sql = "select name from  peers";
					try {
						rs = statement.executeQuery(sql);
						while(rs.next()){
							if ((rs.getString("name") !=null) && (rs.getString("name").length() > 0)){
								String s = rs.getString("name").replace("/", "_");
								try {
									channel.queueDeclare( s, false, false, false, null);
								} catch (IOException e) {
									
								}
							}
						}
					} catch (SQLException e) {
						logger.error("Error executing query Utils.class line 138 with message: " + e.getMessage());
					}
				} catch (SQLException e1) {
					logger.error("Error creating statement Utils.class line 141 with message: " + e1.getMessage());
				}
				finally{
					try {
						rs.close();
					} catch (SQLException e) {
						logger.error("Error closing  result set Utils.class line 147 with message: " + e.getMessage());
					}
				}
		} catch (IOException | TimeoutException  e1) {
			logger.error("Error com.rabbitmq.client.Connection in Utils.class line 151 with message: " + e1.getMessage());
		} 
	}
	public static String getRabbitmqHost() {
		return rabbitmqHost;
	}

	public static String getRabbitmqUsername() {
		return rabbitmqUsername;
	}

	public static String getRabbitmqPpassword() {
		return rabbitmqPpassword;
	}

	public static String getAsterisUser() {
		return asterisUser;
	}

	public static String getAsterisPassword() {
		return asterisPassword;
	}

	public static String getAsteriskHost() {
		return asteriskHost;
	}

	public static int getAsteriskPort() {
		return asteriskPort;
	}

}

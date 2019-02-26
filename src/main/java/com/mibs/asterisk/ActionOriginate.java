package com.mibs.asterisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class ActionOriginate extends AbstractAction implements Action{

	private String channel;
	private String extent;
	private String context;
	
	public ActionOriginate(Socket s) throws IOException {
		super(s);
		
	}
	public ActionOriginate(Socket s,String channel, String extent, String context) throws IOException {
		super(s);
		this.channel = channel;
		this.extent = extent;
		this.context = context;
	}

	@Override
	public void doCommand() throws IOException {
		/*		
		ACTION: Originate
		Channel: SIP/12345
		Exten: 1234
		Priority: 1
		Timeout: 60000
		Context: default
*/		
		writer.write("Action: Originate\r\nChannel:" + channel + "\r\nExten: " + extent + "\r\nPriority: 1\r\nTimeout: 60000\r\nContext:" + context + "\r\n\r\n");
		writer.flush();
	}

	@Override
	public void doCommand(String queue) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Action> getResponce() throws IOException, AuthenticationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedReader getReader() {
		// TODO Auto-generated method stub
		return null;
	}

}

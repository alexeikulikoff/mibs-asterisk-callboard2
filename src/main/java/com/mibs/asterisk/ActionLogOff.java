package com.mibs.asterisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;



public class ActionLogOff extends AbstractAction implements Action{

	@Override
	public AgentState getActionResult() {
		return state;
	}
	public ActionLogOff(Socket s, AgentState rs) throws IOException {
		super(s, rs);
	}
	@Override
	public void doCommand() throws IOException {
		writer.write("Action: Logoff\r\nActionID:12345\r\n\r\n");
		writer.flush();
		writer.close();
		reader.close();
		socket.close();
	}
	@Override
	public Optional<Action> getResponce() throws IOException, AuthenticationFailedException {
		return Optional.empty();
	}
	@Override
	public BufferedReader getReader() {
		
		return reader;
	}
	@Override
	public void doCommand(String queue) throws IOException {
		
		
	}
	
}

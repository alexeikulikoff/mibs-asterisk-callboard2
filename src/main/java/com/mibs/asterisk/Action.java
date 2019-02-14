package com.mibs.asterisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;



public interface Action {
	
	void doCommand() throws IOException;
	void doCommand(String queue) throws IOException;
	
	Optional<Action> getResponce() throws IOException, AuthenticationFailedException;
	BufferedReader getReader();
	default AgentState getActionResult() {
		return AgentState.FAILED;
	}
	
}

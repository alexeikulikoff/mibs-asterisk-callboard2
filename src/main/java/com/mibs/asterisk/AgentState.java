package com.mibs.asterisk;

public enum AgentState {

	REMOVED("REMOVEMEMBER"), ADDED("ADDMEMBER"), FAILED("FAILED");
	private String actionResult;
	private AgentState(String s) {
		actionResult = s;
	}
	public String getActionResult() {
		return actionResult;
	}
	
}

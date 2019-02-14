package com.mibs.callboard;

public interface Wrapper {

	void addAgent(String queue, String phone);
	void removeAgent(String queue, String name);
	void changeAgentStatus(String queue, String name, int status);
	
}

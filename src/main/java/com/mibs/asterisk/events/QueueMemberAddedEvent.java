package com.mibs.asterisk.events;


import com.mibs.callboard.Wrapper;

public class QueueMemberAddedEvent implements AsteriskEvent{
	private String privilege;
	private String queue;
	private String location;
	private String membername;
	private String stateinterface;
	private String membership;
	private String penalty;
	private String callstaken;
	private String lastcall;
	private String status;
	private String paused;
	private String agentname;
	private String queueid;
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getStateinterface() {
		return stateinterface;
	}
	public void setStateinterface(String stateinterface) {
		this.stateinterface = stateinterface;
	}
	public String getMembership() {
		return membership;
	}
	public void setMembership(String membership) {
		this.membership = membership;
	}
	public String getPenalty() {
		return penalty;
	}
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	public String getCallstaken() {
		return callstaken;
	}
	public void setCallstaken(String callstaken) {
		this.callstaken = callstaken;
	}
	public String getLastcall() {
		return lastcall;
	}
	public void setLastcall(String lastcall) {
		this.lastcall = lastcall;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaused() {
		return paused;
	}
	public void setPaused(String paused) {
		this.paused = paused;
	}
	public String getAgentname() {
		return agentname;
	}
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	public String getQueueid() {
		return queueid;
	}
	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	@Override
	public String toString() {
		return "QueueMemberAddedEvent [privilege=" + privilege + ", queue=" + queue + ", location=" + location
				+ ", membername=" + membername + ", stateinterface=" + stateinterface + ", membership=" + membership
				+ ", penalty=" + penalty + ", callstaken=" + callstaken + ", lastcall=" + lastcall + ", status="
				+ status + ", paused=" + paused + ", agentname=" + agentname + ", queueid=" + queueid + "]";
	}
	@Override
	public void execute(Wrapper wrapper) {
		
		wrapper.addAgent(queue, membername);
	}
	
	
}

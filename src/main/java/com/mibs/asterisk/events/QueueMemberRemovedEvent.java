package com.mibs.asterisk.events;


import com.mibs.callboard.Wrapper;

public class QueueMemberRemovedEvent implements AsteriskEvent{
	private String queue;
	private String location;
	private String membername;
	private String queueid;
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
	public String getQueueid() {
		return queueid;
	}
	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	@Override
	public String toString() {
		return "QueueMemberRemovedEvent [queue=" + queue + ", location=" + location + ", membername=" + membername
				+ ", queueid=" + queueid + "]";
	}
	@Override
	public void execute(Wrapper wrapper) {
		wrapper.removeAgent(queue, membername);
		
	}
	
}

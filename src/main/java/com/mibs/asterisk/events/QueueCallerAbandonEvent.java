package com.mibs.asterisk.events;

import org.apache.logging.log4j.LogManager;

import com.mibs.asterisk.AbandonNumber;
import com.mibs.asterisk.AbandonNumberException;
import com.mibs.asterisk.CallerAbandon;
import com.mibs.callboard.App;
import com.mibs.callboard.Wrapper;

public class QueueCallerAbandonEvent implements AsteriskEvent {
	private static final org.apache.logging.log4j.Logger logger =  LogManager.getLogger(QueueCallerAbandonEvent.class.getName());
	private String privilege;
	private String channel;
	private String channelstate;
	private String channelstatedesc;
	private String calleridnum;
	private String calleridname;
	private String connectedlinenum;
	private String connectedlinename;
	private String language;
	private String accountcode; 
	private String context;
	private String exten;
	private String priority;
	private String uniqueid;
	private String linkedid;
	private String queue;
	private String position;
	private String originalposition;
	private String holdtime;

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelstate() {
		return channelstate;
	}

	public void setChannelstate(String channelstate) {
		this.channelstate = channelstate;
	}

	public String getChannelstatedesc() {
		return channelstatedesc;
	}

	public void setChannelstatedesc(String channelstatedesc) {
		this.channelstatedesc = channelstatedesc;
	}

	public String getCalleridnum() {
		return calleridnum;
	}

	public void setCalleridnum(String calleridnum) {
		this.calleridnum = calleridnum;
	}

	public String getCalleridname() {
		return calleridname;
	}

	public void setCalleridname(String calleridname) {
		this.calleridname = calleridname;
	}

	public String getConnectedlinenum() {
		return connectedlinenum;
	}

	public void setConnectedlinenum(String connectedlinenum) {
		this.connectedlinenum = connectedlinenum;
	}

	public String getConnectedlinename() {
		return connectedlinename;
	}

	public void setConnectedlinename(String connectedlinename) {
		this.connectedlinename = connectedlinename;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAccountcode() {
		return accountcode;
	}

	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getExten() {
		return exten;
	}

	public void setExten(String exten) {
		this.exten = exten;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getLinkedid() {
		return linkedid;
	}

	public void setLinkedid(String linkedid) {
		this.linkedid = linkedid;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOriginalposition() {
		return originalposition;
	}

	public void setOriginalposition(String originalposition) {
		this.originalposition = originalposition;
	}

	public String getHoldtime() {
		return holdtime;
	}

	public void setHoldtime(String holdtime) {
		this.holdtime = holdtime;
	}

	@Override
	public String toString() {
		return "QueueCallerAbandonEvent [privilege=" + privilege + ", channel=" + channel + ", channelstate="
				+ channelstate + ", channelstatedesc=" + channelstatedesc + ", calleridnum=" + calleridnum
				+ ", calleridname=" + calleridname + ", connectedlinenum=" + connectedlinenum + ", connectedlinename="
				+ connectedlinename + ", language=" + language + ", accountcode=" + accountcode + ", context=" + context
				+ ", exten=" + exten + ", priority=" + priority + ", uniqueid=" + uniqueid + ", linkedid=" + linkedid
				+ ", queue=" + queue + ", position=" + position + ", originalposition=" + originalposition
				+ ", holdtime=" + holdtime + "]";
	}

	@Override
	public void execute(Wrapper wrapper) {
		try {
			App.callerAbandon.add(new AbandonNumber(this.calleridnum, this.queue));
			logger.trace("Add Phone number: " + this.calleridnum + " to call back.");
		} catch (AbandonNumberException e) {
			logger.trace("Phone number: " + this.calleridnum + " is not added to abandon with message: " + e.getMessage());
		}
		
	}

}

package com.mibs.asterisk.events;

import com.mibs.asterisk.Utils;
import com.mibs.callboard.Wrapper;

public class AgentCalledEvent implements AsteriskEvent {
	private String privilege;
	private String channel;
	private String channelstate;
	private String channelstateDesc;
	private String callerIdnum;
	private String callerIdname;
	private String connectedlinenum;
	private String connectedlinename;
	private String language;
	private String accountcode;
	private String context;
	private String exten;
	private String priority;
	private String uniqueid;
	private String linkedid;
	private String destchannel;
	private String destchannelstate;
	private String destchannelstatedesc;
	private String destcallerIdnum;
	private String destcallerIdname;
	private String destconnectedlinenum;
	private String destconnectedlinename;
	private String destlanguage;
	private String destaccountcode;
	private String destcontext;
	private String destexten;
	private String destpriority;
	private String destuniqueid;
	private String destlinkedid;
	private String queue;
	//private String interface;
	private String mmembermame;
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
	public String getChannelstateDesc() {
		return channelstateDesc;
	}
	public void setChannelstateDesc(String channelstateDesc) {
		this.channelstateDesc = channelstateDesc;
	}
	public String getCallerIdnum() {
		return callerIdnum;
	}
	public void setCallerIdnum(String callerIdnum) {
		this.callerIdnum = callerIdnum;
	}
	public String getCallerIdname() {
		return callerIdname;
	}
	public void setCallerIdname(String callerIdname) {
		this.callerIdname = callerIdname;
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
	public String getDestchannel() {
		return destchannel;
	}
	public void setDestchannel(String destchannel) {
		this.destchannel = destchannel;
	}
	public String getDestchannelstate() {
		return destchannelstate;
	}
	public void setDestchannelstate(String destchannelstate) {
		this.destchannelstate = destchannelstate;
	}
	public String getDestchannelstatedesc() {
		return destchannelstatedesc;
	}
	public void setDestchannelstatedesc(String destchannelstatedesc) {
		this.destchannelstatedesc = destchannelstatedesc;
	}
	public String getDestcallerIdnum() {
		return destcallerIdnum;
	}
	public void setDestcallerIdnum(String destcallerIdnum) {
		this.destcallerIdnum = destcallerIdnum;
	}
	public String getDestcallerIdname() {
		return destcallerIdname;
	}
	public void setDestcallerIdname(String destcallerIdname) {
		this.destcallerIdname = destcallerIdname;
	}
	public String getDestconnectedlinenum() {
		return destconnectedlinenum;
	}
	public void setDestconnectedlinenum(String destconnectedlinenum) {
		this.destconnectedlinenum = destconnectedlinenum;
	}
	public String getDestconnectedlinename() {
		return destconnectedlinename;
	}
	public void setDestconnectedlinename(String destconnectedlinename) {
		this.destconnectedlinename = destconnectedlinename;
	}
	public String getDestlanguage() {
		return destlanguage;
	}
	public void setDestlanguage(String destlanguage) {
		this.destlanguage = destlanguage;
	}
	public String getDestaccountcode() {
		return destaccountcode;
	}
	public void setDestaccountcode(String destaccountcode) {
		this.destaccountcode = destaccountcode;
	}
	public String getDestcontext() {
		return destcontext;
	}
	public void setDestcontext(String destcontext) {
		this.destcontext = destcontext;
	}
	public String getDestexten() {
		return destexten;
	}
	public void setDestexten(String destexten) {
		this.destexten = destexten;
	}
	public String getDestpriority() {
		return destpriority;
	}
	public void setDestpriority(String destpriority) {
		this.destpriority = destpriority;
	}
	public String getDestuniqueid() {
		return destuniqueid;
	}
	public void setDestuniqueid(String destuniqueid) {
		this.destuniqueid = destuniqueid;
	}
	public String getDestlinkedid() {
		return destlinkedid;
	}
	public void setDestlinkedid(String destlinkedid) {
		this.destlinkedid = destlinkedid;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getMmembermame() {
		return mmembermame;
	}
	public void setMmembermame(String mmembermame) {
		this.mmembermame = mmembermame;
	}
	@Override
	public String toString() {
		return "AgentCalledEvent [privilege=" + privilege + ", channel=" + channel + ", channelstate=" + channelstate
				+ ", channelstateDesc=" + channelstateDesc + ", callerIdnum=" + callerIdnum + ", callerIdname="
				+ callerIdname + ", connectedlinenum=" + connectedlinenum + ", connectedlinename=" + connectedlinename
				+ ", language=" + language + ", accountcode=" + accountcode + ", context=" + context + ", exten="
				+ exten + ", priority=" + priority + ", uniqueid=" + uniqueid + ", linkedid=" + linkedid
				+ ", destchannel=" + destchannel + ", destchannelstate=" + destchannelstate + ", destchannelstatedesc="
				+ destchannelstatedesc + ", destcallerIdnum=" + destcallerIdnum + ", destcallerIdname="
				+ destcallerIdname + ", destconnectedlinenum=" + destconnectedlinenum + ", destconnectedlinename="
				+ destconnectedlinename + ", destlanguage=" + destlanguage + ", destaccountcode=" + destaccountcode
				+ ", destcontext=" + destcontext + ", destexten=" + destexten + ", destpriority=" + destpriority
				+ ", destuniqueid=" + destuniqueid + ", destlinkedid=" + destlinkedid + ", queue=" + queue
				+ ", mmembermame=" + mmembermame + "]";
	}
	@Override
	public void execute(Wrapper wrapper) {
		
		Utils.publish(destcallerIdnum, callerIdnum);
		
	}
    
}

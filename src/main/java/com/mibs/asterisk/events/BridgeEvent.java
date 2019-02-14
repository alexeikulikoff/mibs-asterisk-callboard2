package com.mibs.asterisk.events;


import com.mibs.callboard.Wrapper;

public class BridgeEvent implements AsteriskEvent {
	private String bridgestate;
	private String bridgetype;
	private String channel1;
	private String channel2;
	private String uniqueid1;
	private String uniqueid2;
	private String callerid1;
	private String callerid2;
	private String customer;
	public String getBridgestate() {
		return bridgestate;
	}
	public void setBridgestate(String bridgestate) {
		this.bridgestate = bridgestate;
	}
	public String getBridgetype() {
		return bridgetype;
	}
	public void setBridgetype(String bridgetype) {
		this.bridgetype = bridgetype;
	}
	public String getChannel1() {
		return channel1;
	}
	public void setChannel1(String channel1) {
		this.channel1 = channel1;
	}
	public String getChannel2() {
		return channel2;
	}
	public void setChannel2(String channel2) {
		this.channel2 = channel2;
	}
	public String getUniqueid1() {
		return uniqueid1;
	}
	public void setUniqueid1(String uniqueid1) {
		this.uniqueid1 = uniqueid1;
	}
	public String getUniqueid2() {
		return uniqueid2;
	}
	public void setUniqueid2(String uniqueid2) {
		this.uniqueid2 = uniqueid2;
	}
	public String getCallerid1() {
		return callerid1;
	}
	public void setCallerid1(String callerid1) {
		this.callerid1 = callerid1;
	}
	public String getCallerid2() {
		return callerid2;
	}
	public void setCallerid2(String callerid2) {
		this.callerid2 = callerid2;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	@Override
	public String toString() {
		return "BridgeEvent [bridgestate=" + bridgestate + ", bridgetype=" + bridgetype + ", channel1=" + channel1
				+ ", channel2=" + channel2 + ", uniqueid1=" + uniqueid1 + ", uniqueid2=" + uniqueid2 + ", callerid1="
				+ callerid1 + ", callerid2=" + callerid2 + ", customer=" + customer + "]";
	}
	@Override
	public void execute(Wrapper wrapper) {
		System.out.println(this);
		
	}
	
}

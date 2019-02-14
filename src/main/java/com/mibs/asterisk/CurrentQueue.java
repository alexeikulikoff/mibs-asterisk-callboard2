package com.mibs.asterisk;

import java.util.Set;
import java.util.TreeSet;

public class CurrentQueue implements Comparable<CurrentQueue>{

	private String queue;
	private Set<String> members ;
	public CurrentQueue(String q) {
		queue = q;
		members = new TreeSet<>();
	}
	public void addMember(String m) {
		members.add(m);
	}
	public boolean isContainMember(String memberName) {
		for(String s : members) {
			if (s.equals(memberName)) return true;
		}
		return false;
	}
	public Set<String> getMembers(){
		return members;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	@Override
	public String toString() {
		return "[queue=" + queue + ", members=" + members + "]";
	}
	@Override
	public int compareTo(CurrentQueue o) {
		return  queue.compareTo(o.queue);
	}
	
}

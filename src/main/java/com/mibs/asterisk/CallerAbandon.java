package com.mibs.asterisk;


import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;

import com.mibs.callboard.App;

public class CallerAbandon {
	private static final org.apache.logging.log4j.Logger logger =  LogManager.getLogger(App.class.getName());
	private TreeSet<AbandonNumber> content = new TreeSet<>();
	private Socket socket;
	public CallerAbandon(Socket socket) {
		this.socket = socket;
	}
	public void doCallBack(String channel, String context) {
		
		if (!Utils.getCallback().toLowerCase().equals("yes")) return;
		if (content.size() == 0) return;
		ActionOriginate originate;
		String extent = null;
		try {
			extent = content.pollFirst().getNumber();
			Utils.publish(channel, extent);
			if (extent !=null) {
				originate = new ActionOriginate(socket, channel, extent, context );
				originate.doCommand();
			}else {
				logger.error("Error while makeing callback. Extention is null." );
			}
			
		} catch (IOException e) {
			logger.error("Error while makeing callback to extention :" + extent + " from peer:" + channel );
		}
		
	}
	public Set<AbandonNumber> getContent() {
		return content;
	}
	public void add(AbandonNumber a) {
		content.add( a );
	}
	public boolean isContain(String s) {
		boolean result = false;
		for(AbandonNumber a : content) {
			if (a.getNumber().equals(s)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public Optional<AbandonNumber> getAbandonNumber(String num, String queue) {
		AbandonNumber result = null;
		for(AbandonNumber a : content) {
			if (a.getNumber().equals( num ) & a.getQueue().equals(queue) ) {
				result = new AbandonNumber(a.getNumber(),a.getEpoch(), a.getQueue());
				break;
			}
		}
		return (result == null) ? Optional.empty() : Optional.of(result);	
	}
	public boolean remove(String num, String queue) {
		if (getAbandonNumber(num, queue).isPresent()) {
			return content.remove( getAbandonNumber( num, queue ).get());
			
		}else {
			return false;
		}
	}
	public int getSize() {
		return content.size();
	}
	public void cleaAll() {
		content.clear();
	}
	@Override
	public String toString() {
		String s =  "CallerAbandon [\n" ;
		for(AbandonNumber a : content) {
			s += "[ number=" + a.getNumber() + ", epoch=" + a.getEpoch() + ", queue=" + a.getQueue() + "]\n" ; 
		}
		s += "]";
		return s;
	}
	
}

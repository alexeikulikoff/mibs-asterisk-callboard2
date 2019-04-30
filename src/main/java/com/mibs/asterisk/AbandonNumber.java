package com.mibs.asterisk;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class AbandonNumber implements Comparable<AbandonNumber>{

	private String number;
	private Long epoch;
	private String queue;
	
	public AbandonNumber(String number, Long epoch, String queue) {
		this.number = number;
		this.epoch = epoch;
		this.queue = queue;
	}
	public AbandonNumber(String number, String queue) throws AbandonNumberException {
		super();
		if (number.length() != 11) throw new AbandonNumberException("Wrong pnone number length.");
		if (!number.startsWith("89")) throw new AbandonNumberException("Wrong pnone number format");
		if (queue.contains("polyclinic")) {
			throw new AbandonNumberException("Skip polyclinnic queue");
		}
		this.number = number;
		this.queue = queue;
		epoch = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
	}
	public String getNumber() {
		return number;
	}
	public String getQueue() {
		return queue;
	}
	public Long getEpoch() {
		return epoch;
	}
	
	@Override
	public String toString() {
		return "AbandonNumber [number=" + number + ", epoch=" + epoch + ", queue=" + queue + "]";
	}
	@Override
	public boolean equals(Object obj) {
		AbandonNumber other = (AbandonNumber) obj;
		return (number.equals(other.number) & queue.equals(other.queue));
	}
	@Override
	public int compareTo(AbandonNumber o) {
		return (int)( epoch - o.getEpoch());
	}
	
	
}

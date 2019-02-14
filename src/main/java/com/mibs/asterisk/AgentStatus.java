package com.mibs.asterisk;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

public class AgentStatus {
	public  Map<Integer, Color > colors;
	
	public AgentStatus() {
		colors = new TreeMap<>();
		colors.put(0, new Color(51, 51, 255));
		colors.put(1, new Color(0, 153, 51));
		colors.put(2, new Color(204, 153, 0));  // AST_DEVICE_INUSE
		colors.put(3, new Color(204, 41, 0));   // AST_DEVICE_BUSY
		colors.put(4, new Color(179, 0, 89));   // AST_DEVICE_INVALID
		colors.put(5, new Color(153, 51, 51));   //  AST_DEVICE_UNAVAILABLE
		colors.put(6, new Color(255, 0, 0));   //  AST_DEVICE_RINGING
		colors.put(7, new Color(153, 153, 0));   //  AST_DEVICE_RINGINUSE
		colors.put(8, new Color(0, 0, 102));   //  AST_DEVICE_ONHOLD
	}
	
	
}

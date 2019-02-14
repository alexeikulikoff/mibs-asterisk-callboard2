package com.mibs.callboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AgentItem extends JPanel implements Comparable<AgentItem>{

	private static final long serialVersionUID = 1L;
	private String phone;
	private String name;	
	private JLabel caption;
	public AgentItem(String phone) {
		super();
		this.phone = phone.toLowerCase();
	}
	public AgentItem(String phone, String name) {
		super();
		this.phone = phone.toLowerCase();;
		this.name = name;
		setMaximumSize( new Dimension(
                Integer.MAX_VALUE,
                75
        ));
	
		String ph = this.phone;
		if (ph.startsWith("sip")) ph = ph.replace("sip/", "");
		
	    setBackground(new Color(0, 128, 43));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		caption = new JLabel(ph + " " + this.name ) ;
		caption.setFont(new Font("Serif", Font.PLAIN, 36));
		caption.setForeground(Color.white);
		add(caption);
		
	}
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		  
	}
	@Override
	public int compareTo(AgentItem o) {
		return phone.compareTo(o.phone);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentItem other = (AgentItem) obj;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AgentItem [phone=" + phone + ", name=" + name + "] hashCode: " + hashCode();
	}

	

}

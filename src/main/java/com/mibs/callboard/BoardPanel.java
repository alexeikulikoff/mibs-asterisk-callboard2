package com.mibs.callboard;

import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mibs.asterisk.AgentStatus;

import javax.swing.GroupLayout.Group;

public class BoardPanel extends JPanel implements Comparable<BoardPanel>{

	private static final long serialVersionUID = 1L;

	private String queueName;
	
	private Set<AgentItem> agentsItems;
	private  Group parallelGroup;
	private  Group sequentialGroup;
	private AgentStatus agentStatus;
	
	public BoardPanel(String queue, int size) {
		super();
		agentsItems = new TreeSet<>();
		agentStatus = new AgentStatus();
		this.queueName = queue;
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		 
		JLabel queueNameLabel = new JLabel(queueName) ;
		queueNameLabel.setFont(new Font("Serif", Font.PLAIN, 36));
		queueNameLabel.setForeground(Color.white);
        
		JPanel queueNamePanel = new JPanel();
		queueNamePanel.setMaximumSize( new Dimension(
	                Integer.MAX_VALUE,
	                100
	        ));
		queueNamePanel.setBackground(new Color(41,56,70));
		queueNamePanel.setLayout(new GridBagLayout());
		
		queueNamePanel.add(queueNameLabel);
		
		setMaximumSize( new Dimension(
				DimMax.width/ size ,
				Integer.MAX_VALUE
        ));
		setBackground(Color.black);
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
		
	   parallelGroup = layout.createParallelGroup();
	   
	   parallelGroup.addComponent(queueNamePanel);
	   
	   sequentialGroup = layout.createSequentialGroup();
	   
	   sequentialGroup.addComponent(queueNamePanel);
	   
	   layout.setHorizontalGroup(layout.createSequentialGroup()
	    		.addGap(5)
	    		.addGroup(parallelGroup)
	    		.addGap(5)
	    		);
	    layout.setVerticalGroup(layout.createParallelGroup()
	    		.addGap(5)
	    		.addGroup( sequentialGroup)
	    		.addGap(5));
	
	}
	public void addAgent(String phone, String name ) {
		AgentItem item = new AgentItem(phone, name);
		agentsItems.add( item );
		parallelGroup.addComponent( item );
		sequentialGroup.addComponent( item );
		revalidate();
		repaint();
		
	}
	public void changeAgentStatus(String phone, int status ) {
		AgentItem item = new AgentItem(phone);
		Component[] componentList = getComponents();
		for(Component c : componentList){
		    if(c.equals(item)){
		        c.setBackground(agentStatus.colors.get( status ));
		    }
		}
		revalidate();
		repaint();	
	}
	public void removeAgent(String phone) {
		AgentItem item = new AgentItem(phone);
		boolean res = agentsItems.remove( item );
		if (res) {
			Component[] componentList = getComponents();
			for(Component c : componentList){
			    if(c.equals(item)){
			        remove(c);
			    }
			}
			revalidate();
			repaint();
		}
	}
	@Override
	public int compareTo(BoardPanel o) {
		return queueName.compareTo(o.queueName);
	}
	public String getQueueName() {
		return queueName;
	}



}

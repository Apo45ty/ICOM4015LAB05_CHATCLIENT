package ece.uprm.edu.icom4015.chatapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EnterPanel extends SimplePanel{

	@Override
	void init() {
		JButton eButton = new JButton("Enter");
		eButton.setActionCommand("enterRoom");
		eButton.setName("enter");
		
		
		String[] roomNames = getChatNames();
		
		JComboBox<String> combo = new JComboBox<String>(roomNames);
		combo.setName("roomDropDown");
		combo.setActionCommand("checkRoom");
		
		JTextField field = new JTextField("Username", 2);
		field.setName("nameField");
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(0,5));
		
		buttonPane.add(new JLabel("Name:"));
		buttonPane.add(field);
		buttonPane.add(new JLabel("Room Number:"));
		buttonPane.add(combo);
		buttonPane.add(eButton);
		buttonPane.setPreferredSize(new Dimension(630, 30));
		
		JPanel mainPanel = new JPanel();
		mainPanel.add(buttonPane,BorderLayout.CENTER);
		mainPanel.setPreferredSize(new Dimension(630,400));
		this.add(mainPanel,BorderLayout.CENTER);
		
		JLabel status = new JLabel("Welcome");
		status.setName("status");
		status.setPreferredSize(new Dimension(630, 30));
		this.add(status,BorderLayout.PAGE_END);
		
		this.setBorder(new EmptyBorder(20,20,20,20));
	}

	private String[] getChatNames() {
		String knownNames[] = {
				"b/",
				"irc",
				"Mofongo"
		};
		String roomNames[] = new String[20]; 
		for(int i=1;i<=20;i++){
			roomNames[i-1]=knownNames[(i-1)%knownNames.length]+((i-1)/knownNames.length);
		}
		return roomNames;
	}
	
	/**
	 * Test UI
	 * @param args
	 */
	public static void main(String... args){
		JFrame window = new JFrame();
		window.setContentPane(new EnterPanel());
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(680, 520);
	}
}

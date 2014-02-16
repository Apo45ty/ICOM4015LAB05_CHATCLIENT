package ece.uprm.edu.icom4015.chatapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChatPanel extends SimplePanel{

	@Override
	void init() {
		
		/**
		 * Text Area
		 */
		JTextArea chatArea = new JTextArea("Welcome");
		chatArea.setName("chatArea");
		chatArea.setPreferredSize(new Dimension(630,300));
		chatArea.setEditable(false);
		
		/**
		 * Button Bar
		 */
		JButton sButton = new JButton("Send!");
		sButton.setActionCommand("sendMessage");
		sButton.setName("send");
		sButton.setPreferredSize(new Dimension(50, 30));
		
		JTextField messageField = new JTextField("Hey, sup?");
		messageField.setName("messageField");
		messageField.setPreferredSize(new Dimension(550, 30));
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(0,2));
		buttonPane.setPreferredSize(new Dimension(630, 30));
		buttonPane.add(messageField);
		buttonPane.add(sButton);
		
		/**
		 * Main Pane
		 */
		JPanel chatPane = new JPanel();
		chatPane.add(chatArea,BorderLayout.CENTER);
		chatPane.add(buttonPane,BorderLayout.PAGE_END);
		chatPane.setPreferredSize(new Dimension(630,350));
		
		/**
		 * Status Label
		 */
		JLabel status = new JLabel("Welcome");
		status.setName("status");
		status.setPreferredSize(new Dimension(630, 30));
		
		/**
		 * Exit Button
		 */
		JButton exitRoom = new JButton("Exit Room!");
		exitRoom.setName("exitRoom");
		exitRoom.setActionCommand("exitRoom");	
		exitRoom.setPreferredSize(new Dimension(630,30));
		/**
		 * Main Panel
		 */
		this.add(exitRoom,BorderLayout.PAGE_START);
		this.add(chatPane,BorderLayout.CENTER);
		this.add(status,BorderLayout.PAGE_END);
		
		this.setBorder(new EmptyBorder(20,20,20,20));
		
	}
	
	/**
	 * Test UI
	 * @param args
	 */
	public static void main(String... args){
		JFrame window = new JFrame();
		window.setContentPane(new ChatPanel());
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(680, 520);
	}

}

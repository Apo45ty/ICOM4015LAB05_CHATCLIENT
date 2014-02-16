package ece.uprm.edu.icom4015.chatapp;
/**
 * 
 */


import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author anton_000
 *
 */
public abstract class SimplePanel extends JPanel {
	ChatListener listener;
	
	/**
	 *  Initialize all buttons and call super class
	 */
	public SimplePanel() {
		super();
		init();
	}
	
	/**
	 * Initialize listener
	 * @param listener
	 */
	public SimplePanel(ChatListener listener) {
		this();
		setChatListener(listener,null);
	}
	
	/**
	 * add the listener to all other subclasses
	 * @param listener
	 */
	private void setChatListener(ChatListener listener,Container container){
		container = container == null ?this:container; // for recursive call
		for(int i=0;i<container.getComponentCount();i++){
			Component comp = container.getComponent(i);
			if(comp instanceof JButton){
				JButton butt = (JButton)comp;
				butt.addActionListener(listener);
			} else if(comp instanceof JTextField){
				JTextField field = (JTextField)comp;
				field.addActionListener(listener);
			} else if(comp instanceof JComboBox){
				JComboBox field = (JComboBox)comp;
				field.addActionListener(listener);
			}else if(comp instanceof JTextArea){
				JTextArea text = (JTextArea) comp;
				text.addKeyListener(listener);
			} else if(comp instanceof JLabel){
				JLabel text = (JLabel) comp;
				text.addKeyListener(listener);
			}else if(comp instanceof Container){
				//recursive call
				setChatListener(listener,(Container)comp); // add listeners to all subcomponents
			} 
		}
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void addChatListener(ChatListener listener) {
	 	setChatListener(listener,null);
	}
	
	/**
	 * Create the UI don't mind adding the listener
	 */
	abstract void init();
	
}

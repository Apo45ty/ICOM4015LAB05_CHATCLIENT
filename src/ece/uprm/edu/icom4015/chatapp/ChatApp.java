package ece.uprm.edu.icom4015.chatapp;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author anton_000
 *
 */
public class ChatApp implements WindowListener{
	/**
	 * Default package access modifier
	 */
	JFrame mainPane;
	ChatEngine ngin;
	
	/**
	 * Sets all variables properly
	 * @param mainPane main window that hold the panels
	 * @param ngin  Engine that handels all logic
	 * @param cPanel panel that handels all the chat
	 * @param ePanel panel that handles the welcoming
	 * @param listener Default chatListener should be used.
	 */
	public ChatApp(JFrame mainPane, ChatEngine ngin, SimplePanel cPanel,
			SimplePanel ePanel, ChatListener listener) {
		this(mainPane,ngin,cPanel,ePanel);
		
		if(listener.getNgin() == null){
			listener.setNgin(ngin);
		}
		
		ChatListener[] listenersE = ePanel.getListeners(ChatListener.class);
		ChatListener[] listenersC = cPanel.getListeners(ChatListener.class);
//		System.out.println("Listeners enter " + listenersE);
//		System.out.println("Listeners chat " + listenersC);
		
		if(listenersC.length <= 0){
			cPanel.addChatListener(listener); // add the listener
		}
		
		if(listenersE.length <= 0){
			ePanel.addChatListener(listener); // add the listener
		}
	}
	
	/**
	 * @Assumes Listener is already added and listener has the engine added
	 * @param mainPane
	 * @param ngin
	 * @param cPanel
	 * @param ePanel
	 */
	public ChatApp(JFrame mainPane, ChatEngine ngin, SimplePanel cPanel,
			SimplePanel ePanel) {
		super();
		this.mainPane = mainPane;
		this.ngin = ngin;
		
		/*
		 * Set all necesary panels for the ngin 
		 */
		if(ngin.getCpanel() == null ){
			ngin.setCpanel(cPanel);
		} 
		if(ngin.getEpanel() == null){
			ngin.setEpanel(ePanel);
		}
		if(ngin.getWindow() == null){
			ngin.setWindow(mainPane);
		}
	}
	
	
	/**
	 * Start the chat app 
	 */
	public void start(){
		mainPane.getContentPane().add(ngin.getEpanel());
		mainPane.addWindowListener(this);
		mainPane.setVisible(true);
		mainPane.setDefaultCloseOperation(mainPane.EXIT_ON_CLOSE);
		mainPane.setSize(680, 520);
		ngin.start();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ngin.stop();
		try{
			ngin.exitRoom();
			
		} catch(Exception except){
			System.out.println(except);
		
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}

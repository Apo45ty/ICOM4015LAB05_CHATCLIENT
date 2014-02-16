package ece.uprm.edu.icom4015.chatapp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author anton_000
 *
 */
public class ChatListener implements ActionListener,KeyListener{

	ChatEngine ngin;
	
	public ChatListener(){
		ngin=null;
	}
	
	public ChatListener(ChatEngine ngin) {
		this.ngin=ngin;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if(action.equalsIgnoreCase("checkRoom")){
			ngin.checkRoom();
		} else if(action.equalsIgnoreCase("enterRoom")){
			ngin.setName();
			ngin.enterRoom();
		} else if(action.equalsIgnoreCase("sendMessage")){
			ngin.sendMessage();
		} else if(action.equalsIgnoreCase("exitRoom")){
			ngin.exitRoom();
		} 
	}

/**
	 * @return the ngin
	 */
	public ChatEngine getNgin() {
		return ngin;
	}

	/**
	 * @param ngin the ngin to set
	 */
	public void setNgin(ChatEngine ngin) {
		this.ngin = ngin;
	}

@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		ngin.userTyped(arg0.getKeyChar());
	}

	

}

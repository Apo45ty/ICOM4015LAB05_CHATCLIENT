package ece.uprm.edu.icom4015.chatapp;

import java.net.URL;

import javax.swing.JTextField;

public class MyChatEngine extends ChatEngine {

	public MyChatEngine(SimplePanel cpanel, SimplePanel epanel) {
		super(cpanel, epanel);
	}

	@Override
	public void setName() {
		JTextField name = (JTextField)fetchComponent(null, "nameField");
		this.name = name.getText();
	}

	@Override
	public boolean checkRoomImp(String roomNumber, URL url) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean enterRoomImp(String roomNumber, URL url) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean sendMessageImp(String message, URL url) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exitRoomImp(String name2, String roomNum2, URL server2) {
		// TODO Auto-generated method stub
		
	}

}

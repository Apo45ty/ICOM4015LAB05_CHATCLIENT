package ece.uprm.edu.icom4015.chatapp;

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
	public boolean checkRoomImp(String roomName, String url) {
		String[] parameters = {
			"room="+roomName	
		};
		String result = HTTPConnetor.sendGet(url,parameters , System.out);
		
		return result.contains("\"success\":true");
	}

	@Override
	public boolean registerUserInRoomImp(String roomName, String name, String url) {
		String[] parameters = {
			"room="+roomName,
			"name="+name
		};
		String result = HTTPConnetor.sendPost(url,parameters , System.out);
		logStatus(result.split("\"description\":")[1]);
		return result.contains("\"success\":true");
	}


	@Override
	public boolean sendMessageImp(String roomName, String name, String message,
			MyWebSocket socket) {
		String JSON = "{"+
				"\"name\":"+"\""+name+"\""+
				",\"message\":"+"\""+message+"\""+
				",\"room\":"+"\""+roomName+"\""+
				",\"isMessage\":true"+
				",\"acknowledge\":true"+
		"}";
		
		String responce = socket.sendAndWaitAcknowledgment(JSON);
		return responce.contains("\"success\":true");
	}

	@Override
	public boolean exitRoomImp(String roomName, String name, String url) {
		String[] parameters = {
			"room="+roomName,
			"name="+name
		};
		String result = HTTPConnetor.sendPost(url,parameters , System.out);
		return result.contains("\"success\":true");
	}

	

}

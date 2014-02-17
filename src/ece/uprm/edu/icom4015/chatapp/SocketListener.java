package ece.uprm.edu.icom4015.chatapp;

import org.eclipse.jetty.websocket.api.Session;

public interface SocketListener {

	void onConnect(Session session);

	void onMessage(String msg);

	void onClose(String reason);

}

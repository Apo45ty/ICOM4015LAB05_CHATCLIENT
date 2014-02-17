package ece.uprm.edu.icom4015.chatapp;

import java.awt.Component;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * Finite State Automata for one UI
 * 
 * @author anton_000
 * 
 */
public abstract class ChatEngine implements Runnable, SocketListener {
	private SimplePanel cpanel;
	private SimplePanel epanel;
	private JFrame window;
	private boolean inChat = false;
	protected String name;
	private String roomName;
	private String server = "http://localhost:5001/v1/chat";
	AwesomeHttpURLConnection HTTPConnetor;
	private WebSocketClient client;
	private MyWebSocket socket;
	/**
	 * @param cpanel
	 * @param epanel
	 * @param window
	 */
	public ChatEngine(SimplePanel cpanel, SimplePanel epanel, JFrame window) {
		this(cpanel, epanel);
		this.window = window;
	}

	/**
	 * @param cpanel
	 * @param epanel
	 */
	public ChatEngine(SimplePanel cpanel, SimplePanel epanel) {
		super();
		this.cpanel = cpanel;
		this.epanel = epanel;
		this.HTTPConnetor = new AwesomeHttpURLConnection();
		client = new WebSocketClient();
	}

	/**
	 * fetches the name of the use and stores it in the this.name variable
	 */
	public abstract void setName();

	/**
	 * Check if Room has space for registration
	 */
	public abstract boolean checkRoomImp(String roomName, String url);

	/**
	 * Registered in room
	 */
	public abstract boolean registerUserInRoomImp(String roomName,String name, String url);

	/**
	 * Send message to server and return true if message is send
	 */
	public abstract boolean sendMessageImp(String roomName,String name,String message, MyWebSocket socket);

	/**
	 * Tell the server a good by message
	 */
	public abstract boolean exitRoomImp( String roomNam, String name, String server);

	public void userTyped(char keyChar) {
		String json = 
				"{"+
						"name:"+name+
						",room:"+roomName+
						",isTyping:true"+
				"}";
		
		socket.send(json);
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		if (!inChat) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		//You can implement the engine as a consumer between the that has a queue of instructons

	}
	
	/**
	 * 
	 */
	public void onConnect(Session session){
		
	}

	/**
	 *  Received message from socket
	 */
	public void onMessage(String msg){
		updateChatArea(msg);
	}
	
	/**
	 * 
	 * @param msg
	 */
	private void updateChatArea(String msg) {
		JTextArea chatArea = (JTextArea) fetchComponent(null, "chatArea");
		chatArea.setText(chatArea.getText()+(new java.util.Date())+"\n\r"+msg);
	}

	/**
	 * Socket is closed
	 */
	public void onClose(String reason){
		try {
			client.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Change the status label to display the current status
	 * 
	 * @param status
	 */
	private void updateStatus(String status) {
		JLabel label = (JLabel) fetchComponent(null, "status");
		label.setText(status);
	}

	/**
	 * Fetches the component and returns it to. Has default access or package
	 * access modifier
	 * 
	 * @param name
	 * @return null if componen is not found, otherwise returns the first
	 *         component with the given name
	 */
	Component fetchComponent(Container container, String name) {
		Container currentPanel = container == null ? window.getContentPane()
				: container; // for recursive call
		for (int i = 0; i < currentPanel.getComponentCount(); i++) {
			Component component = currentPanel.getComponent(i);
			if (component instanceof JPanel) {
				// do forced checking for the container
				component = fetchComponent((Container) component, name);
				if (component != null) {
					return component; // if I found the component nested inside
										// one of the containers return it
				}
			} else if (component.getName() != null
					&& component.getName().equals(name)) {
				// base case
				return currentPanel.getComponent(i);
			}
		}
		return null;
	}


	/**
	 * Fetch the Chat JPanel
	 * @return
	 */
	public SimplePanel getCpanel() {
		return cpanel;
	}

	/**
	 * Set the Chat JPanel
	 * @param cpanel
	 */
	public void setCpanel(SimplePanel cpanel) {
		this.cpanel = cpanel;
	}

	/**
	 * 
	 * @return
	 */
	public SimplePanel getEpanel() {
		return epanel;
	}

	/**
	 * 
	 * @param epanel
	 */
	public void setEpanel(SimplePanel epanel) {
		this.epanel = epanel;
	}

	/**
	 * @return the window
	 */
	public JFrame getWindow() {
		return window;
	}

	/**
	 * @param window
	 *            the window to set
	 */
	public void setWindow(JFrame window) {
		this.window = window;
	}

	/**
	 * Get the room number from the ePanel
	 * 
	 * @return
	 */
	private String fetchRoom() {
		JComboBox<String> combo = (JComboBox<String>) fetchComponent(null,
				"roomDropDown");
		return (String) combo.getSelectedItem();
	}

	/**
	 * Get the message from the cPanel
	 * @return
	 */
	private String fetchMessage() {
		JTextField message = (JTextField) fetchComponent(null, "messageField");
		return message.getText();
	}

	/**
	 * Handles the verifng of a single room in a server
	 */
	public void checkRoom() {
		String roomName = fetchRoom();
		boolean roomHasSpace = checkRoomImp(roomName, server+"/checkRoom");
		if (roomHasSpace) {
			updateStatus("This Room has Space.");
			// TODO Do other operations
		} else {
			updateStatus("This Room does not have Space.");
		}
	}
	
	/**
	 *  This method is called when the user wants to register and chat it both registers the user and it establishes a socket server side connection
	 */
	public void enterRoom() {
		String roomName = fetchRoom();
		inChat = registerUserInRoomImp(roomName, name,server+"/registerUser");
		if (inChat) {
			window.setContentPane(cpanel);
			this.roomName=roomName;
			updateStatus("Conected to Chat Room " + roomName + ".");
			//do Web Socket
			URI url = null;
			try {
				url = new URI(server.replace("http://", "ws://"));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			socket = new MyWebSocket();
			socket.addSocketListener(this);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            try {
				client.start();
	            client.connect(socket, url, request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			updateStatus("Can't connect to Room !");
		}
	}
	
	/**
	 * This method is called when a message needs to be send to the other end point
	 */
	public void sendMessage() {
		String message = fetchMessage();
		sendMessageImp(roomName,name,message,socket);
	}

	/**
	 * This method is called when an user desires to terminate the connection to the chat and also desires to unregister from the chat.
	 */
	public void exitRoom() {
		inChat = false;
		window.setContentPane(epanel);
		if(exitRoomImp(roomName, name, server+"/unregisterUser")){
			updateStatus("Disconected from Room "+roomName+".");
		} else {
			updateStatus("Could not disconect from Room"+roomName+"!");
		}
	}

	protected class AwesomeHttpURLConnection {

		private final String USER_AGENT = "JavaChat";

		/**
		 * Sends an HTTP GET request
		 * @param url
		 *            to send the reques
		 * @param parameters
		 *            of parameters already arranged in \'key=value\' form
		 * @param out
		 *            put stream
		 * @return server responce
		 * @throws Exception
		 */
		public String sendGet(String url, String[] parameters, PrintStream out) {
			out = out == null ? System.out : out;
			if (parameters != null) {
				url += "?";
				for (int i = 0; i < parameters.length; i++) {
					url += parameters[i];
				}
			}
			String result = "";

			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj
						.openConnection();

				// optional default is GET
				con.setRequestMethod("GET");

				// add request header
				con.setRequestProperty("User-Agent", USER_AGENT);

				int responseCode = con.getResponseCode();
				out.println("\nSending 'GET' request to URL : " + url);
				out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				result = response.toString();
				// print result
				out.println(result);
			} catch (Exception e) {

			}
			return result;
		}

		/**
		 * Sends an HTTP POST request 
		 * @param url where to post to !
		 * @param parameters string array in the format of "key=value" for a single string, only works for string parameters
		 * @param out PrintStream
		 */
		public String sendPost(String url, String[] parameters, PrintStream out) {
			out = out == null ? System.out : out;
			String result = "";
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				String urlParameters = "";
				
				// add request header
				con.setRequestMethod("POST");
				if (parameters != null) {
					for (int i = 0; i < parameters.length; i++) {
						String key = parameters[i].split("=")[0];
						String value = parameters[i].split("=")[1];
						out.println(key+"="+value);
						urlParameters+=key+"="+value+"&";
						con.setRequestProperty(key,value);
					}
				}
				con.setRequestProperty("User-Agent", USER_AGENT);
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				
				out.println(urlParameters);
				
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(
						con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();

				int responseCode = con.getResponseCode();
				out.println("\nSending 'POST' request to URL : " + url);
				out.println("Post parameters : " + urlParameters);
				out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				out.println(response.toString());
				result = response.toString();
			} catch (Exception e) {
					out.println(e.toString());
			}
			
			return result;
		}

	}
	
	/**
	 * Basic Echo Client Socket
	 */
	@WebSocket
	public class MyWebSocket {
		private List<SocketListener> listeners;
	    private final CountDownLatch closeLatch;
	    
	    @SuppressWarnings("unused")
	    private Session session;
	 
	    public MyWebSocket() {
	        this.closeLatch = new CountDownLatch(1);
	        listeners = new LinkedList<SocketListener>();
	    }
	 
	    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
	        return this.closeLatch.await(duration, unit);
	    }
	 
	    @OnWebSocketClose
	    public void onClose(int statusCode, String reason) {
	        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
	        this.session = null;
	        this.closeLatch.countDown();
	        for(SocketListener listener:this.listeners){
		    	listener.onClose(reason);
	        }
	    }
	    
	    /**
	     * Sends a message to the other socket end point
	     * @param message to send to the end point
	     */
	    public void send(String message){
	    	Future<Void> fut;
            fut = session.getRemote().sendStringByFuture(message);
            try {
				fut.get(2, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException
					| TimeoutException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * on connect event listener
	     * @param session
	     */
	    @OnWebSocketConnect
	    public void onConnect(Session session) {
	        this.session = session;
	        try {
	            
	            for(SocketListener listener:this.listeners){
			    	listener.onConnect(session);
		        }
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	    }
	 
	    /**
	     * on mesage event listener
	     * @param msg received from the other end point
	     */
	    @OnWebSocketMessage
	    public void onMessage(String msg) {
	        for(SocketListener listener:this.listeners){
		    	listener.onMessage(msg);
	        }
	    }
	    
	    public void addSocketListener(SocketListener listener){
	    	listeners.add(listener);
	    }
	}
	
	// View component names
	// enter
	// nameField
	// roomDropDown
	// messageField
	// send
	// chatArea
	// status
	// exitRoom
}

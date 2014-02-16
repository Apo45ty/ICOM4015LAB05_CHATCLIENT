package ece.uprm.edu.icom4015.chatapp;
import java.awt.Component;
import java.awt.Container;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Finite State Automata for one UI
 * @author anton_000
 *
 */
public abstract class ChatEngine implements Runnable{
	private SimplePanel cpanel;
	private SimplePanel epanel;
	private JFrame window;
	private boolean inChat = false;
	private boolean updates = false;
	private List<String> lines = new LinkedList<String>();
	protected String name;
	private String roomNum;
	private URL server;
	
	/**
	 * @param cpanel
	 * @param epanel
	 * @param window
	 */
	public ChatEngine(SimplePanel cpanel, SimplePanel epanel,JFrame window) {
		this(cpanel,epanel);
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
	}
	
	/**
	 * fetches the name of the use and stores it in the this.name variable
	 */
	public abstract void setName();
	
	/**
	 * Check if Room has space for registration 
	 */
	public abstract boolean checkRoomImp(String roomNumber, URL url);

	/**
	 * Registered in room 
	 */
	public abstract boolean enterRoomImp(String roomNumber, URL url);

	/**
	 * Send message to server and return true if message is send
	 */
	public abstract boolean sendMessageImp(String message, URL url);
	
	/**
	 * Tell the server a good by message
	 * @param name2 of user that just exited
	 * @param roomNum2
	 * @param server2
	 */
	public abstract void exitRoomImp(String name2, String roomNum2, URL server2);
	
	public void userTyped(char keyChar) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void run() {
		if(!inChat){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		getChatUpdates();
		if(updates){
			updateChat();
		}
		
	}
	
	/**
	 * Change the status label to display the current status
	 * @param status
	 */
	private void updateStatus(String status) {
		JLabel label = (JLabel)fetchComponent(null,"status");
		label.setText(status);
	}
	
	/**
	 * Fetches the component and returns it to. Has default access or package access modifier
	 * @param name
	 * @return null if componen is not found, otherwise returns the first component with the given name
	 */
	Component fetchComponent(Container container,String name) {
		Container currentPanel = container == null ?window.getContentPane():container; // for recursive call
		for(int i=0;i<currentPanel.getComponentCount();i++){
			Component component = currentPanel.getComponent(i);
			if(component instanceof JPanel){
				// do forced checking for the container
				component = fetchComponent((Container)component,name);
				if(component != null){
					return component; // if I found the component nested inside one of the containers return it
				}
			} else if(component.getName()!=null && component.getName().equals(name)){
				//base case
				return currentPanel.getComponent(i);
			}
		}
		return null;
	}

	/**
	 * Update the UI with the new lines
	 */
	private void updateChat() {
		// TODO Auto-generated method stub
		updateStatus("Updating User Interface");
	}
	
	/**
	 * Fetch any changes in the line array via post
	 */
	private boolean getChatUpdates() {
		// TODO Auto-generated method stub
		updateStatus("Checking for updates the chat dialog.");
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public SimplePanel getCpanel() {
		return cpanel;
	}
	
	/**
	 * 
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
	 * @param window the window to set
	 */
	public void setWindow(JFrame window) {
		this.window = window;
	}

	/**
	 * Get the room number from the ePanel
	 * @return
	 */
	private String fetchRoom() {
		JComboBox<String> combo = (JComboBox<String>)fetchComponent(null, "roomDropDown");
		return (String)combo.getSelectedItem();		
	}
	
	/**
	 * Get the message from the cPanel
	 * @return
	 */
	private String fetchMessage() {
		JTextField message = (JTextField)fetchComponent(null, "messageField");
		return message.getText();
	}

	/**
	 * 
	 */
	public void checkRoom() {
		String roomNumber=fetchRoom();
		boolean roomHasSpace = checkRoomImp(roomNumber, server);
		if(roomHasSpace){
			updateStatus("This Room has Space.");
			//TODO Do other operations
		} else {
			updateStatus("This Room does not have Space.");
		}
	}


	public void enterRoom() {
		String roomNumber=fetchRoom();
		inChat = enterRoomImp(roomNumber, server);
		if(inChat){
			window.setContentPane(cpanel);
			updateStatus("Conected to Chat Room "+roomNum+".");
		} else {
			updateStatus("Can't connect to Room !");
		}
	}

	public void sendMessage() {
		// TODO Auto-generated method stub
		String message=fetchMessage();
		sendMessageImp(message, server);
	}

	public void exitRoom() {
		inChat = false;
		window.setContentPane(epanel);
		exitRoomImp(name,roomNum,server);
	}

	
	
	//enter
	//nameField
	//roomDropDown
	//messageField
	//send
	//chatArea
	//status
	//exitRoom
}

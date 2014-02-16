package ece.uprm.edu.icom4015.chatapp;

import javax.swing.JFrame;

public class MyChatFrameRunner {

	public static void main(String[] args) {
		SimplePanel chatPanel = new ChatPanel();
		SimplePanel enterPanel = new EnterPanel();
		ChatEngine myEngine = new MyChatEngine(chatPanel, enterPanel);
		ChatListener listener = new ChatListener(myEngine);
		ChatApp app = new ChatApp(new JFrame(),myEngine, chatPanel,enterPanel,listener);
		
		app.start();
	}

}

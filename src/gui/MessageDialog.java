package gui;

import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class MessageDialog {


	JDialog conversation;
	
	
	public MessageDialog(){
		conversation = new JDialog(null, "Conversation", Dialog.ModalityType.MODELESS);
		setupGUI();
		conversation.pack();
		conversation.setVisible(true);
	}
	
	public MessageDialog(String dialogName){
		conversation = new JDialog(null, dialogName, Dialog.ModalityType.MODELESS);
		setupGUI();
		conversation.pack();
		conversation.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MessageDialog test = new MessageDialog();
	}

	private void setupGUI(){
		BoxLayout bl = new BoxLayout(conversation.getContentPane(), BoxLayout.Y_AXIS);
		conversation.setLayout(bl); 
		//TODO: for adding a menu later
	//	conversation.setJMenuBar(menu);
		JPanel convoPanel = new JPanel();
		convoPanel.setPreferredSize(new Dimension(300, 200));
		JScrollPane scrollWindow = new JScrollPane();
		scrollWindow.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollWindow.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JEditorPane convoWindow = new JEditorPane();
		convoWindow.setContentType("text/plain");
		convoWindow.setText("The first test!");
		//TODO: Will need a method that keeps a large string of the conversation and inserts usernames and shit
		
		scrollWindow.add(convoWindow);
		convoPanel.add(scrollWindow);
		conversation.getContentPane().add(convoPanel);
		JTextField inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(300, 150));
		conversation.add(inputField);
	}
	
}

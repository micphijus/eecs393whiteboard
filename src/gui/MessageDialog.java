package gui;

import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		JPanel convoWindow = new JPanel();
		convoWindow.setPreferredSize(new Dimension(300, 200));
		conversation.add(convoWindow);
		JTextField inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(300, 150));
		conversation.add(inputField);
	}
	
}

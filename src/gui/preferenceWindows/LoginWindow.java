package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jivesoftware.smack.XMPPConnection;

import core.network.ChatboardConnection;

import gui.AbstractWindow;
import gui.MainWindow;
import gui.MessageDialog;
import gui.WindowFactory.WindowType;

public class LoginWindow extends AbstractWindow {

	private JTextField screenNameIn;
	private JPasswordField passwordIn;
	private ChatboardConnection conn;
	
	public LoginWindow(String title, JFrame parent) {
		super();
		setTitle(title);
		setParent(parent);
		build();
		window.setPreferredSize(new Dimension(300, 200));
		window.pack();
		window.setVisible(true);
		window.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public ActionListener applyListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildWindow() {
		// TODO make better! Use gridbag layout or something ALSO don't let the text fields auto expand
		//TODO: also make the "Login" part of the enum?
		JLabel title = new JLabel("Login");
		
		screenNameIn = new JTextField();
		passwordIn = new JPasswordField();
		
		screenNameIn.addKeyListener(enterAction());
		passwordIn.addKeyListener(enterAction());
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("Username: "));
		labelPanel.add(new JLabel("Password: "));
		valuePanel.add(screenNameIn);
		valuePanel.add(passwordIn);
		 
		JPanel formPanel = new JPanel(new BorderLayout(5,5));
		formPanel.add(labelPanel, BorderLayout.WEST);
		formPanel.add(valuePanel, BorderLayout.CENTER);
		 
		JPanel theFinalOne = new JPanel(new BorderLayout(5,5));
		theFinalOne.add(formPanel, BorderLayout.NORTH);
		
		window.add(title);
		window.add(theFinalOne);
	}


	@Override
	public ActionListener okListener() {
		// TODO Auto-generated method stub
		
		//if you want to do things with the text values, just use screenNameIn.getText() and passwordIn.getText()!
		//might have to make some variables "final" too just FYI
		ActionListener login = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				login(screenNameIn.getText(), passwordIn.getPassword());
				window.dispose();
			}
		};
		return login;
	}
	
	public void login(String userName, char[] password)
	{
		String thePassword = "";
		for(int i = 0; i < password.length; i++)
			thePassword = thePassword + password[i];
		conn = new ChatboardConnection(userName, thePassword);
		conn.createConnection("talk.google.com", 5222, "gmail.com");
		
	}

	public ChatboardConnection getConn() {
		return conn;
	}

}

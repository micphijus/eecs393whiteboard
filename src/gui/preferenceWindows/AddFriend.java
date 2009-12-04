package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.im.ChatboardRoster;

import gui.AbstractWindow;
import gui.MainWindow;
import gui.WindowFactory.WindowType;

public class AddFriend extends AbstractWindow {

	private JTextField screenNameIn;
	private JTextField aliasNameIn;	
	private ChatboardRoster theRoster;
	
	
	public AddFriend(String title, Window parent){
		super();
		setTitle(title);
		setParent(parent);
		
		build();
		window.setPreferredSize(new Dimension(300, 200));
		window.pack();
		window.setVisible(true);

		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	
	@Override
	public ActionListener applyListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionListener okListener() {
		ActionListener al = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("this is a test!!");
				setRoster(MainWindow.getRoster());
				String [] groups = {"Contacts"};
				try
				{
					theRoster.addBuddy(screenNameIn.getText(), aliasNameIn.getText(), groups);
					JOptionPane.showMessageDialog(window,
							"Sent a notification.  You must wait until your friend accepts", 
							"Confirmation", 
							JOptionPane.INFORMATION_MESSAGE,
							new ImageIcon(MainWindow.fishIcon));
					
					window.dispose();
				}
				catch(Exception e1)
				{
					System.out.println("Cannot register name");
					screenNameIn.setText("");
					aliasNameIn.setText("");
					e1.printStackTrace();
				}
				
			}
		};
		return al;
	}
	//TODO: Remove
	public static void main(String[] args){
		AddFriend test = new AddFriend("Add Friend", null);

	}

	@Override
	//TODO: also make them select protocol!
	protected void buildWindow() {
		JLabel title = new JLabel(WindowType.AddFriend.getPrintString());
		
		screenNameIn = new JTextField();
		aliasNameIn = new JTextField();
		
		screenNameIn.addKeyListener(enterAction());
		aliasNameIn.addKeyListener(enterAction());
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("Screen Name: "));
		labelPanel.add(new JLabel("Alias: "));
		valuePanel.add(screenNameIn);
		valuePanel.add(aliasNameIn);
		 
		JPanel formPanel = new JPanel(new BorderLayout(5,5));
		formPanel.add(labelPanel, BorderLayout.WEST);
		formPanel.add(valuePanel, BorderLayout.CENTER);
		 
		JPanel theFinalOne = new JPanel(new BorderLayout(5,5));
		theFinalOne.add(formPanel, BorderLayout.NORTH);
		
		window.add(title);
		window.add(theFinalOne);
	}
	public void setRoster(ChatboardRoster roster)
	{
		theRoster = roster;
	}
}

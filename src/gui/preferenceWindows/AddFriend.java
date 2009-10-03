package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.AbstractWindow;

public class AddFriend extends AbstractWindow {

	public AddFriend(String title, Window parent){
		super();
		setTitle(title);
		setParent(parent);
		build();
		window.setPreferredSize(new Dimension(300, 200));
		window.pack();
		window.setVisible(true);
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
				
			}
		};
		return al;
	}
	//TODO: Remove
	public static void main(String[] args){
		AddFriend test = new AddFriend("Add Friend", null);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	@Override
	protected void buildWindow() {
		// TODO make better! Use gridbag layout or something ALSO don't let the text fields auto expand
		JLabel title = new JLabel("Add Friend");
		
		JTextField screenNameIn = new JTextField();
		JTextField aliasNameIn = new JTextField();
		
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
}

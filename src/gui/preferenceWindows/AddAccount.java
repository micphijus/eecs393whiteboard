package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.AbstractWindow;
import gui.MainWindow;
import gui.WindowFactory.WindowType;

public class AddAccount extends AbstractWindow {
	
	public AddAccount(String title, Window parent){
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
	protected void buildWindow() {
		JLabel title = new JLabel(WindowType.AddAccount.getPrintString());
		
		JTextField screenNameIn = new JTextField();

		JComboBox chooseAccount = new JComboBox(new String[] {"AIM", "MSN", "Yahoo","Gchat", "Jabber"});
		
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("Screen Name: "));
		labelPanel.add(new JLabel("Account Type: "));
		valuePanel.add(screenNameIn);
		valuePanel.add(chooseAccount);
		 
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
		return null;
	}
}

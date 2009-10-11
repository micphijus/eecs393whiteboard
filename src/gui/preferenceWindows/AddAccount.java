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

	//TODO: remove default constructor
	public AddAccount(){
		JOptionPane.showMessageDialog(MainWindow.getInstance(), "A test message!", "Add Account", JOptionPane.INFORMATION_MESSAGE);
	}
	
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
		// TODO make better! Use gridbag layout or something ALSO don't let the text fields auto expand
		JLabel title = new JLabel(WindowType.AddAccount.getPrintString());
		
		JTextField screenNameIn = new JTextField();

		//TODO make this static strings or an enum or something
		JComboBox chooseAccount = new JComboBox(new String[] {"AIM", "MSN", "Yahoo", "GChat"});
		
		
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
	//TODO: Remove
	public static void main(String[] args){
		AddAccount test = new AddAccount("Add Account", null);

	}
}

package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.AbstractWindow;
import gui.MainWindow;
import gui.WindowFactory.WindowType;

public class AddGroup extends AbstractWindow {
	
	public JTextField groupNameIn;
	
	public AddGroup(String title, Window parent){
		super();
		setTitle(title);
		setParent(parent);
		build();
		window.setPreferredSize(new Dimension(300, 200));
		window.pack();
		window.setVisible(true);

		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	//TODO: Remove the no-args constructor
	public AddGroup(){
		JOptionPane.showMessageDialog(MainWindow.getInstance(), "A second test message!", "Add Friend", JOptionPane.INFORMATION_MESSAGE);

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
				MainWindow.roster.createGroup(groupNameIn.getText());
				window.dispose();
			}
		};
		return al;
	}
	//TODO: Remove
	public static void main(String[] args){
		AddGroup test = new AddGroup("Add Group", null);

	}

	@Override
	protected void buildWindow() {
		// TODO make better! Use gridbag layout or something ALSO don't let the text fields auto expand
		JLabel title = new JLabel(WindowType.AddGroup.getPrintString());
		
		groupNameIn = new JTextField();
		
		groupNameIn.addKeyListener(enterAction());
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("Enter New Group Name: "));
		valuePanel.add(groupNameIn);
		
		 
		JPanel formPanel = new JPanel(new BorderLayout(5,5));
		formPanel.add(labelPanel, BorderLayout.WEST);
		formPanel.add(valuePanel, BorderLayout.CENTER);
		 
		JPanel theFinalOne = new JPanel(new BorderLayout(5,5));
		theFinalOne.add(formPanel, BorderLayout.NORTH);
		
		window.add(title);
		window.add(theFinalOne);
	}
}

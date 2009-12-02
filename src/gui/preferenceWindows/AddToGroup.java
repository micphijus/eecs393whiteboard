package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class AddToGroup extends AbstractWindow {

	private JTextField groupNameIn;
	private JTextField screenNameIn;
	private ChatboardRoster theRoster;

	public AddToGroup(String title, Window parent){
		super();
		setTitle(title);
		setParent(parent);
		
		build();
		window.setPreferredSize(new Dimension(500, 200));
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
		JLabel title = new JLabel(WindowType.AddToGroup.getPrintString());
		
		groupNameIn = new JTextField();
		screenNameIn = new JTextField();
		
		groupNameIn.addKeyListener(enterAction());
		screenNameIn.addKeyListener(enterAction());
		
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("User Name: "));
		labelPanel.add(new JLabel("Group to add to: "));
		valuePanel.add(screenNameIn);
		valuePanel.add(groupNameIn);
		 
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
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		};
		return al;
	}
	public void setRoster(ChatboardRoster roster)
	{
		theRoster = roster;
	}
}


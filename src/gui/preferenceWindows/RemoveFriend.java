package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.AbstractWindow;
import gui.WindowFactory.WindowType;

public class RemoveFriend extends AbstractWindow {

	private JTextField removeNameIn;

	public RemoveFriend(String title, Window parent){
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
JLabel title = new JLabel(WindowType.ViewLog.getPrintString());
		
		removeNameIn = new JTextField();
		
		removeNameIn.addKeyListener(enterAction());
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("Enter full Username to remove user (include the @): "));
		valuePanel.add(removeNameIn);
		
		 
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

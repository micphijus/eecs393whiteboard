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

public class RemoveFriend extends AbstractWindow {

	private JTextField removeNameIn;
	private ChatboardRoster theRoster;

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
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setRoster(MainWindow.getRoster());
				try
				{
					theRoster.removeBuddy(removeNameIn.getText());
					JOptionPane.showMessageDialog(window,
							"You just removed a friend", 
							"Confirmation", 
							JOptionPane.INFORMATION_MESSAGE,
							new ImageIcon(MainWindow.fishIcon));
					
					window.dispose();
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(window,
							"Could not remove friend", 
							"Error", 
							JOptionPane.ERROR_MESSAGE,
							new ImageIcon(MainWindow.fishIcon));
					removeNameIn.setText("");
				}
			}
		};
		return al;
	}
	public void setRoster(ChatboardRoster roster)
	{
		theRoster = roster;
	}
}

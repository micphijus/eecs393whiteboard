package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.AbstractWindow;
import gui.MainWindow;
import gui.MessageDialog;
import gui.WindowFactory.WindowType;

public class EnterNameWindow extends AbstractWindow {

	private JTextField usernameIn;
	
	public EnterNameWindow(String title, JFrame parent) {
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
		JLabel title = new JLabel(WindowType.NewIM.getPrintString());
		
		usernameIn = new JTextField();
		usernameIn.addKeyListener(enterAction());
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("Enter the username to IM: "));
		valuePanel.add(usernameIn);
		
		 
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
		ActionListener newIM = new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameIn.getText();
				MessageDialog conversation = new MessageDialog(username);
				conversation.addController(MainWindow.getController());
				MainWindow.getController().addDialog(conversation, username);
				window.dispose();
			}
		};
		return newIM;
	}
	
}

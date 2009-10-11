package gui.preferenceWindows;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.AbstractWindow;

public class AboutWindow extends AbstractWindow {
//TODO: hide cancel button! Doesn't make sense here!
	public AboutWindow(String title, Window parent){
		super();
		setTitle(title);
		setParent(parent);
		build();
		window.setPreferredSize(new Dimension(400, 200));
		window.pack();
		window.setVisible(true);

		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public AboutWindow(){
		
	}
	
	@Override
	public ActionListener applyListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildWindow() {
		// TODO Auto-generated method stub
		JLabel about = new JLabel("Chatboard ALPHA version 0.1");
		JLabel by = new JLabel("By Justin Michel, Jeff Willebrand, Felix Yuan");
		JLabel questions = new JLabel("Questions or comments? E-mail chatboard@case.edu");
		addToPanel(about);
		addToPanel(by);
		addToPanel(questions);
	}

	@Override
	public ActionListener okListener() {
		// TODO Auto-generated method stub
		return null;
	}
//TODO: remove!!
	public static void main(String args[]){
		AboutWindow test = new AboutWindow("About", null);
	}
}

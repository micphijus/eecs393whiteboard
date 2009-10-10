package gui.preferenceWindows;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.AbstractWindow;
import gui.MainWindow;

public class AddAccount extends AbstractWindow {

	//TODO: remove default constructor
	public AddAccount(){
		JOptionPane.showMessageDialog(MainWindow.getInstance(), "A test message!", "Add Account", JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public ActionListener applyListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public ActionListener okListener() {
		// TODO Auto-generated method stub
		return null;
	}

}

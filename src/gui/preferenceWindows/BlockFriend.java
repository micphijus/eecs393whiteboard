package gui.preferenceWindows;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import gui.AbstractWindow;

public class BlockFriend extends AbstractWindow {

	public BlockFriend(String title, Window parent){
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
		// TODO Auto-generated method stub

	}

	@Override
	public ActionListener okListener() {
		// TODO Auto-generated method stub
		return null;
	}

}

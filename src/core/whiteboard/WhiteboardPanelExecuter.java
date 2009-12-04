package core.whiteboard;

import java.awt.Dialog;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JDialog;

import core.whiteboard.WhiteboardPanel;

public class WhiteboardPanelExecuter {

	public static void main(String[] args) {
		
		JDialog whiteboard = new JDialog(null, "Whiteboard", Dialog.ModalityType.MODELESS);
		
		BoxLayout bl = new BoxLayout(whiteboard.getContentPane(), BoxLayout.Y_AXIS);
		whiteboard.setLayout(bl);

		WhiteboardPanel wPanel = new WhiteboardPanel();
		wPanel.setPreferredSize(new Dimension(650, 550));
		whiteboard.add(wPanel);
		
		whiteboard.pack();
		whiteboard.setVisible(true);
		whiteboard.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
	

}

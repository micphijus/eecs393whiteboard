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
		wPanel.setPreferredSize(new Dimension(550, 550));
		whiteboard.add(wPanel);
		
		whiteboard.pack();
		whiteboard.setVisible(true);
		whiteboard.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Queue<String> inQueue = new LinkedList<String>();
		inQueue.add("drawLineT,0x00FF00,10,10,10,390,1");
		inQueue.add("drawLineT,0xFFFF00,10,390,390,390,1");
		inQueue.add("drawLineT,0x00FFFF,390,390,390,10,3");
		inQueue.add("drawLineT,0x0000FF,390,10,10,10,3");
		wPanel.applyQueue(inQueue);
	}
	

}

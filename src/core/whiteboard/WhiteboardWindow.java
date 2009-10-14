package core.whiteboard;

import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class WhiteboardWindow {
	JDialog whiteboard;

	class Display extends JPanel implements MouseListener, MouseMotionListener  {

		private int prevX, prevY;  // Most recently processed mouse coords.
		private boolean dragging;  // Set to true when dragging is in process.

		Display() {
			// This constructor simply sets the background color
			// of the panel to be black and sets the panel to
			// listen for mouse events on itself.
			setBackground(Color.black);
			addMouseListener(this);
			addMouseMotionListener(this);
		}


		public void mousePressed(MouseEvent evt) {
			dragging = true;
			prevX = evt.getX();  // Remember starting position.
			prevY = evt.getY();

			Graphics g = getGraphics();
			g.setColor(Color.white);
			g.fillOval( prevX-5, prevY-5, 10, 10 );
			g.dispose();
		}

		public void mouseDragged(MouseEvent evt) {
			if ( dragging == false )  
				return;       
			prevX = evt.getX(); // Current mouse position
			prevY = evt.getY();
			
			Graphics g = getGraphics();
			g.setColor(Color.white);
			g.fillOval( prevX-5, prevY-5, 10, 10 );
			g.dispose();
		}

		public void mouseMoved(MouseEvent evt) {}	//	Mouse moved when not clicked

		public void mouseReleased(MouseEvent evt) {
			if ( dragging == false )  
				return;               
			dragging = false;
			Graphics g = getGraphics();
			g.setColor(Color.white);
			g.fillOval( prevX-5, prevY-5, 10, 10 );
			g.dispose();
		}

		public void mouseEntered(MouseEvent evt) { }
		public void mouseExited(MouseEvent evt) { }
		public void mouseClicked(MouseEvent evt) { }

	} 

	public WhiteboardWindow(){
		whiteboard = new JDialog(null, "Whiteboard", Dialog.ModalityType.MODELESS);
		setupGUI();
		whiteboard.pack();
		whiteboard.setVisible(true);
	}

	public WhiteboardWindow(String dialogName){
		whiteboard = new JDialog(null, dialogName, Dialog.ModalityType.MODELESS);
		setupGUI();
		whiteboard.pack();
		whiteboard.setVisible(true);
	}

	public static void main(String[] args) {
		WhiteboardWindow test = new WhiteboardWindow();
	}

	private void setupGUI(){
		BoxLayout bl = new BoxLayout(whiteboard.getContentPane(), BoxLayout.Y_AXIS);
		whiteboard.setLayout(bl); 
		Display display = new Display();
		display.setPreferredSize(new Dimension(500, 500));
		whiteboard.add(display);
	}


}

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
	Display display;

	class Display extends JPanel implements MouseListener, MouseMotionListener  {

		private int prevX, prevY;  // Most recently processed mouse coords.
		private boolean dragging;  // Set to true when dragging is in process.

		Display() {
			// This constructor simply sets the background color
			// of the panel to be black and sets the panel to
			// listen for mouse events on itself.
			setBackground(Color.white);
			addMouseListener(this);
			addMouseMotionListener(this);
		}


		public void mousePressed(MouseEvent evt) {
			dragging = true;
			prevX = evt.getX();  // Remember starting position.
			prevY = evt.getY();

			drawCircle(Color.black, prevX, prevY, 5);
		}

		public void mouseDragged(MouseEvent evt) {
			if ( dragging == false )  
				return;       
			prevX = evt.getX(); // Current mouse position
			prevY = evt.getY();
			
			drawCircle(Color.black, prevX, prevY, 5);
			
		}

		public void mouseMoved(MouseEvent evt) {}	//	Mouse moved when not clicked

		public void mouseReleased(MouseEvent evt) { 
			
			if ( dragging == false )  
				return;               
			dragging = false;
			
			drawCircle(Color.black, prevX, prevY, 5);
		}

		public void mouseEntered(MouseEvent evt) { }
		public void mouseExited(MouseEvent evt) { }
		public void mouseClicked(MouseEvent evt) { }
		
		public int drawCircle(Color color, int x, int y, int radius ) 
		{
			if (x < 1 || y < 1)
				return 1;
			if (radius < 0 || radius > 50)
				return 2;
//			if (x > 214 && x < 267 && y > 217 && y < 272 )
//				return 3;
			Graphics g = getGraphics();
			g.setColor(Color.black);
			g.fillOval( x-radius, y-radius, radius*2, radius*2 );
			g.dispose();
			return 0;
		}

	}
	public int drawCircle(Color color, int x, int y, int radius ) 
	{ 
		return display.drawCircle(color, x, y, radius);
		
	}

	public WhiteboardWindow(){
		whiteboard = new JDialog(null, "Whiteboard", Dialog.ModalityType.MODELESS);
		setupGUI();
		whiteboard.pack();
		whiteboard.setVisible(true);
		whiteboard.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public WhiteboardWindow(String dialogName){
		whiteboard = new JDialog(null, dialogName, Dialog.ModalityType.MODELESS);
		setupGUI();
		whiteboard.pack();
		whiteboard.setVisible(true);
		whiteboard.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/*public static void main(String[] args) {
		WhiteboardWindow test = new WhiteboardWindow();
	}*/

	private void setupGUI(){
		BoxLayout bl = new BoxLayout(whiteboard.getContentPane(), BoxLayout.Y_AXIS);
		whiteboard.setLayout(bl);
		display = new Display();
		display.setPreferredSize(new Dimension(500, 500));
		whiteboard.add(display);
	}


}

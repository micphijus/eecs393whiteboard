package core.whiteboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

class Display extends JPanel implements MouseListener, MouseMotionListener  {

	private static final long serialVersionUID = 1L;
	private int prevX, prevY, curX, curY;  // Most recently processed mouse coords.
	private boolean dragging;  // Set to true when dragging is in process.

	Display() {
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
	}


	public void mousePressed(MouseEvent evt) {
		dragging = true;
		curX = evt.getX();  // Remember starting position.
		curY = evt.getY();

		//drawCircle(getColor(), prevX, prevY, 5);
	}

	public void mouseDragged(MouseEvent evt) {
		if ( dragging == false )  
			return;
		prevX = curX;
		prevY = curY;
		curX = evt.getX(); 
		curY = evt.getY();
		
		drawLineT(getColor(), prevX, prevY, curX, curY, 1);
	}

	public void mouseMoved(MouseEvent evt) {}	//	Mouse moved when not clicked

	public void mouseReleased(MouseEvent evt) { 
		
		if ( dragging == false )  
			return;               
		dragging = false;
		
		prevX = curX;
		prevY = curY;
		curX = evt.getX(); 
		curY = evt.getY();
		
		drawLineT(getColor(), prevX, prevY, curX, curY, 1);
		//drawCircle(getColor(), prevX, prevY, 5);
	}
	
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
	public void mouseClicked(MouseEvent evt) { }
	
	
	public int drawLineT(Color color, int x1, int y1, int x2, int y2, int thickness)
	{
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		
		BasicStroke wideStroke = new BasicStroke(1.0f);

		g2.setStroke(wideStroke);
		g2.setColor(color);
		g2.drawLine(x1, y1, x2, y2);
		
		g2.dispose();
		g.dispose();
		return 0;
	}
	
	public int drawLine(Color color, int x1, int y1, int x2, int y2, int thickness)
	{
		Graphics g = getGraphics();
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
		g.dispose();
		return 0;
	}
	
	
	public int drawCircle(Color color, int x, int y, int radius ) 
	{
		if (x < 1 || y < 1)
			return 1;
		if (radius < 0 || radius > 50)
			return 2;
		Graphics g = getGraphics();
		g.setColor(color);
		g.fillOval( x-radius, y-radius, radius*2, radius*2 );
		g.dispose();
		return 0;
	}
	
	private Color getColor()
	{
		return Color.black;
	}

}

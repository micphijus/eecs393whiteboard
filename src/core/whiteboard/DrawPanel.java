package core.whiteboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

class DrawPanel extends JPanel implements MouseListener, MouseMotionListener  {

	private static final long serialVersionUID = 1L;
	private int prevX, prevY, curX, curY;  
	private boolean dragging;  // true when dragging occurs
	private Queue<String> commandQueue;
	private Color drawColor;

	DrawPanel() {
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		SetColor(Color.black);
		commandQueue = new LinkedList<String>();
	}


	public void mousePressed(MouseEvent evt) {
		if (evt.getButton()!= MouseEvent.BUTTON1)
			return;
		dragging = true;
		curX = evt.getX();  
		curY = evt.getY();
		
	}

	public void mouseDragged(MouseEvent evt) {
		int pressed = evt.getButton();
		//if (pressed != MouseEvent.MOUSE_PRESSED)
			//return;
		if ( dragging == false )  
			return;
		prevX = curX;
		prevY = curY;
		curX = evt.getX(); 
		curY = evt.getY();
		
		drawLineT(GetColor(), prevX, prevY, curX, curY, 1, true);
	}

	public void mouseMoved(MouseEvent evt) {}	//	Mouse moved when not clicked

	public void mouseReleased(MouseEvent evt) {
		int pressed = evt.getButton();
		if (pressed != MouseEvent.BUTTON1)
			return;
		if ( dragging == false )  
			return;               
		dragging = false;
		
		prevX = curX;
		prevY = curY;
		curX = evt.getX(); 
		curY = evt.getY();
		
		drawLineT(GetColor(), prevX, prevY, curX, curY, 1, true);
	}
	
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
	public void mouseClicked(MouseEvent evt) { }
	
	
	public int drawLineT(Color color, int x1, int y1, int x2, int y2, int thickness, boolean queue)
	{
		Graphics g = getGraphics(); 
		Graphics2D g2 = (Graphics2D)g;
		
		BasicStroke wideStroke = new BasicStroke(1.0f);

		g2.setStroke(wideStroke);
		g2.setColor(color);
		g2.drawLine(x1, y1, x2, y2);
		
		if (queue) commandQueue.add("drawLineT,"+color.toString()+","+x1+","+y1+","+x2+","+y2+","+thickness);
		
		g2.dispose();
		g.dispose();
		return 0;
	}
	
	public int drawLine(Color color, int x1, int y1, int x2, int y2, boolean queue)
	{
		Graphics g = getGraphics();
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
		
		if (queue) commandQueue.add("drawLine,"+color.toString()+","+x1+","+y1+","+x2+","+y2);
		
		g.dispose();
		return 0;
	}
	
	protected Queue<String> getCommandQueue()
	{
		return commandQueue;
	}
	
	protected Color GetColor()
	{
		return drawColor;
	}
	
	protected void SetColor(Color newcolor)
	{
		drawColor = newcolor;
	}

}

package core.whiteboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

class DrawPanel extends JPanel implements MouseListener, MouseMotionListener  {

	private static final long serialVersionUID = 1L;
	private int prevX, prevY, curX, curY;  
	private boolean dragging;  
	private Queue<String> recentComQueue, fullCommandQueue;
	private CurrentDrawConfig curConfig;
	private Timer timer = new Timer(1000, new TimeListener(){});	//Allows whiteboard to refresh and show drawings properly.

	DrawPanel(CurrentDrawConfig newConfig) {
		timer.start();
		setBackground(Color.white);
		setDoubleBuffered(false);
		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(this);
		addMouseMotionListener(this);
		curConfig = newConfig;
		recentComQueue = new LinkedList<String>();
		fullCommandQueue = new LinkedList<String>();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		displayQueue(g);
	}
	
	/** 
	 * Gives 
	 * @param inQueue
	 * @return 0 for success
	 */
	public int applyQueue(Queue<String> inQueue)
	{		
		// Version 1: no synchronization between the 2 queues
		// TODO: add timestamps, sync
		int size;
		if (inQueue != null) 
		{
			size = inQueue.size();
			for (int i = 0; i < size; i++)
			{
				fullCommandQueue.add(inQueue.poll());
			}
		}
		if (recentComQueue != null)
		{
			size = recentComQueue.size();
			for (int i = 0; i < recentComQueue.size(); i++)
			{
				fullCommandQueue.add(recentComQueue.poll());
			}
		}
				
		return 0;
	}
	/** 
	 * Applies the full command Queue (containing all drawing details) 
	 * to whiteboard, along with the unsynced command queue.
	 * @return 0 for success, 1 for NumberFormatException (usually from Color.decode) 
	 * */
	public int displayQueue(Graphics g)
	{
		String[] params;
		String command;
		int size;
		if (fullCommandQueue != null) 
		{
			size= fullCommandQueue.size(); 
			for (int i=0; i < size; i++)
			{
				command = fullCommandQueue.poll(); //Remove null element
				fullCommandQueue.add(command);
				if (command != null)
				{
					params = command.split(",");
					if (params[0].equals("drawLineT")) {
						try {
							this.drawLineT(Color.decode(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]), Float.parseFloat(params[6]), false, g);
						} catch ( NumberFormatException e) { return 1; }		
					}
				}
			}
		}
		
		if (recentComQueue != null) 
		{
			size = recentComQueue.size(); 
			for (int i=0; i < size; i++)
			{
				command = recentComQueue.poll(); //Remove null element
				recentComQueue.add(command);
				if (command != null)
				{
					params = command.split(",");
					if (params[0].equals("drawLineT")) {
						try {
							this.drawLineT(Color.decode(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]), Float.parseFloat(params[6]), false, g);
						} catch ( NumberFormatException e) { return 1; }		
					}
				}
			}
		}
		return 0;
	}
	
	public void mousePressed(MouseEvent evt) {
		if (evt.getButton()!= MouseEvent.BUTTON1)
			return;
		dragging = true;
		curX = evt.getX();  
		curY = evt.getY();
		
	}

	public void mouseDragged(MouseEvent evt) {
		if ( dragging == false )  
			return;
		prevX = curX;
		prevY = curY;
		curX = evt.getX(); 
		curY = evt.getY();
		
		drawLineT(GetColor(), prevX, prevY, curX, curY, GetThickness(), true);
	}

	public void mouseReleased(MouseEvent evt) {
		if (evt.getButton() != MouseEvent.BUTTON1)
			return;
		if ( dragging == false )  
			return;              
		dragging = false;
		
		prevX = curX;
		prevY = curY;
		curX = evt.getX(); 
		curY = evt.getY();
		
		drawLineT(GetColor(), prevX, prevY, curX, curY, GetThickness(), true);
	}
	
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
	public void mouseClicked(MouseEvent evt) { }
	public void mouseMoved(MouseEvent evt) {}	//	Mouse moved when not clicked
	
	public int drawLineT(Color thecolor, int x1, int y1, int x2, int y2, float thickness, boolean queue, Graphics g)
	{ 
		Graphics2D g2 = (Graphics2D)g;

		BasicStroke wideStroke = new BasicStroke(thickness);
		

		g2.setStroke(wideStroke);
		g2.setColor(thecolor);
		g2.drawLine(x1, y1, x2, y2);
		
		String colorStr = Integer.toHexString(thecolor.getRGB());
		colorStr = colorStr.substring(2);	// mask the bits properly
		
		if (queue) recentComQueue.add("drawLineT,0x"+colorStr+","+x1+","+y1+","+x2+","+y2+","+thickness);
	
		return 0;
	}
	public int drawLineT(Color thecolor, int x1, int y1, int x2, int y2, float thickness, boolean queue)
	{ 
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		
		BasicStroke wideStroke = new BasicStroke(thickness);
		

		g2.setStroke(wideStroke);
		g2.setColor(thecolor);
		g2.drawLine(x1, y1, x2, y2);
		
		String colorStr = Integer.toHexString(thecolor.getRGB());
		colorStr = colorStr.substring(2);	// mask the bits properly
		
		if (queue) recentComQueue.add("drawLineT,0x"+colorStr+","+x1+","+y1+","+x2+","+y2+","+thickness);
			
		return 0;
	}
	
	public Queue<String> getQueue()
	{
		return recentComQueue;
	}
	
	public Color GetColor()
	{
		return curConfig.GetColor();
	}
	
	public float GetThickness()
	{
		return curConfig.GetThickness();
	}
	
	private class TimeListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			repaint();
		}	
	}
}


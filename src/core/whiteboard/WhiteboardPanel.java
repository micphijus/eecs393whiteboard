package core.whiteboard;

import java.awt.Dimension;

import javax.swing.JPanel;

import java.awt.*;
import java.util.Queue;


public class WhiteboardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private DrawPanel drawPanel;
	private ColorPanel colorpanel;
	private Queue<String> commandQueue;

	public int drawCircle(Color color, int x, int y, int radius ) 
	{ 
		return drawPanel.drawCircle(color, x, y, radius);
	}

	public int drawLine(Color color, int x1, int y1, int x2, int y2, int thickness ) 
	{ 
		return drawPanel.drawLine(color, x1, y1, x2, y2, thickness);
	}

	public int drawLineT(Color color, int x1, int y1, int x2, int y2, int thickness ) 
	{ 
		return drawPanel.drawLineT(color, x1, y1, x2, y2, thickness);
	}
	
	public WhiteboardPanel(){
		
		this.setLayout(new GridLayout(1,2));

		colorpanel = new ColorPanel();
		drawPanel = new DrawPanel();

		colorpanel.setPreferredSize(new Dimension(100, 250));
		drawPanel.setPreferredSize(new Dimension(500, 500));
		
		this.add(colorpanel);
		this.add(drawPanel);
	}
	public void sendCommands(){
		commandQueue = drawPanel.getCommandQueue();
		
	}

}

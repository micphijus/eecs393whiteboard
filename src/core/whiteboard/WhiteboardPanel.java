package core.whiteboard;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import com.sun.net.ssl.internal.ssl.Debug;

import java.awt.*;
import java.util.Queue;


public class WhiteboardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private DrawPanel drawPanel;
	private ColorPanel colorpanel;
	private Queue<String> outQueue;

	public int drawLine(Color color, int x1, int y1, int x2, int y2, boolean queue) 
	{ 
		return drawPanel.drawLine(color, x1, y1, x2, y2, queue);
	}

	public int drawLineT(Color color, int x1, int y1, int x2, int y2, int thickness, boolean queue ) 
	{ 
		return drawPanel.drawLineT(color, x1, y1, x2, y2, thickness, queue);
	}
	
	public WhiteboardPanel(){
		
		colorpanel = new ColorPanel();
		colorpanel.setPreferredSize(new Dimension(100,250));
		colorpanel.setMinimumSize(new Dimension(100,250));
		colorpanel.setMaximumSize(new Dimension(100,250));
		
		drawPanel = new DrawPanel();
		drawPanel.setPreferredSize(new Dimension(500,500));
		drawPanel.setMinimumSize(new Dimension(500,500));
		drawPanel.setMaximumSize(new Dimension(500,500));
//		
//		this.add(colorpanel);
//		this.add(drawPanel);
		
		GroupLayout WBLayout = new GroupLayout(this);
		this.setLayout(WBLayout);
		WBLayout.setAutoCreateGaps(true);
		WBLayout.setAutoCreateContainerGaps(true);
		WBLayout.setHorizontalGroup(WBLayout.createSequentialGroup()	
			.addComponent(colorpanel)
			.addComponent(drawPanel)
		);
		
		WBLayout.setVerticalGroup(WBLayout.createSequentialGroup()
			.addGroup(WBLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(colorpanel)
				.addComponent(drawPanel))
		);
	}
	public void getCommandQueue(){
		outQueue = drawPanel.getCommandQueue();
		//SendQueue(outQueue);
		
	}
	
	/** Applies the specified Queue to whiteboard*/
	public int applyQueue(Queue<String> inQueue)
	{
		String command = "";
		String[] params;
		command = inQueue.poll();
		while (command != null)
		{
			params = command.split(",");
			if (params[0].equals("drawLine")) {
				drawPanel.drawLine(Color.decode(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]), false);
			}
			else if (params[0].equals("drawLineT")) {
				drawPanel.drawLineT(Color.decode(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]), Integer.parseInt(params[6]), false);
			}
				
		}
		return 0;
	}

}

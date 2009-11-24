package core.whiteboard;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.sun.net.ssl.internal.ssl.Debug;

import java.awt.*;
import java.util.Queue;


public class WhiteboardPanel extends JPanel implements ListDataListener{

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	private static final long serialVersionUID = 1L;
	private DrawPanel drawPanel;
	private ColorPanel colorpanel;
	private Queue<String> outQueue;

	public int drawLine(Color color, int x1, int y1, int x2, int y2, boolean toQueue) 
	{ 
		return drawPanel.drawLine(color, x1, y1, x2, y2, toQueue);
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
		drawPanel.setPreferredSize(new Dimension(400,400));
		drawPanel.setMinimumSize(new Dimension(400,400));
		drawPanel.setMaximumSize(new Dimension(400,400));
		
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
	
	/** Applies the specified Queue to whiteboard
	 * @return 0 for success, 1 for NumberFormatException (usually from Color.decode) 
	 * */
	public int applyQueue(Queue<String> inQueue)
	{
		String[] params;
		String command = inQueue.poll();
		while (command != null)
		{
			params = command.split(",");
			if (params[0].equals("drawLine")) {
				try {
					drawPanel.drawLine(Color.decode(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]), false);
				} catch ( NumberFormatException e) { return 1; }
			}
			else if (params[0].equals("drawLineT")) {
				try {
					drawPanel.drawLineT(Color.decode(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]), Integer.parseInt(params[6]), false);
				} catch ( NumberFormatException e) { return 1; }
				
			}
			command = inQueue.poll();
		}
		return 0;
	}

}

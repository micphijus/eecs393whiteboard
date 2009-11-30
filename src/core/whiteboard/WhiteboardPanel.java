package core.whiteboard;

import java.awt.Color;
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
	protected Color currentColor;

//	public int drawLine(Color color, int x1, int y1, int x2, int y2, boolean toQueue) 
//	{ 
//		return drawPanel.drawLine(color, x1, y1, x2, y2, toQueue);
//	}

	public int drawLineT(Color color, int x1, int y1, int x2, int y2, int thickness, boolean queue ) 
	{ 
		return drawPanel.drawLineT(color, x1, y1, x2, y2, thickness, queue);
	}
	
	public WhiteboardPanel()
	{		
		CurrentColor currentColor = new CurrentColor(Color.black);

		colorpanel = new ColorPanel(currentColor);
		colorpanel.setPreferredSize(new Dimension(100,250));
		colorpanel.setMinimumSize(new Dimension(100,250));
		colorpanel.setMaximumSize(new Dimension(100,250));
		
		drawPanel = new DrawPanel(currentColor);
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
	
	public Queue<String> getQueue()
	{
		return drawPanel.getQueue();
	}
	
	/** Applies the specified Queue to whiteboard
	 * @return 0 for success, 1 for NumberFormatException (usually from Color.decode) 
	 * */
	public int applyQueue(Queue<String> inQueue)
	{	
		return drawPanel.applyQueue(inQueue);
	}

}

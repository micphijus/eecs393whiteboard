package core.whiteboard;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.sun.net.ssl.internal.ssl.Debug;

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
	private ColorPanel colorpanel;
	private ThicknessPanel thicknesspanel;
	private DrawPanel drawPanel;
	
	public int drawLineT(Color color, int x1, int y1, int x2, int y2, int thickness, boolean queue ) 
	{ 
		return drawPanel.drawLineT(color, x1, y1, x2, y2, thickness, queue);
	}
	
	public WhiteboardPanel()
	{		
		CurrentDrawConfig currentDrawConfig = new CurrentDrawConfig(Color.black, 1.0f);

		colorpanel = new ColorPanel(currentDrawConfig);
		colorpanel.setPreferredSize(new Dimension(100,250));
		colorpanel.setMinimumSize(new Dimension(100,250));
		colorpanel.setMaximumSize(new Dimension(100,250));
		
		thicknesspanel = new ThicknessPanel(currentDrawConfig);
		thicknesspanel.setPreferredSize(new Dimension(100,250));
		thicknesspanel.setMinimumSize(new Dimension(100,250));
		thicknesspanel.setMaximumSize(new Dimension(100,250));
		
		drawPanel = new DrawPanel(currentDrawConfig);
		drawPanel.setPreferredSize(new Dimension(400,400));
		drawPanel.setMinimumSize(new Dimension(400,400));
		drawPanel.setMaximumSize(new Dimension(400,400));
		
		GroupLayout WBLayout = new GroupLayout(this);
		this.setLayout(WBLayout);
		WBLayout.setAutoCreateGaps(true);
		WBLayout.setAutoCreateContainerGaps(true);
		WBLayout.setHorizontalGroup(WBLayout.createSequentialGroup()
			.addComponent(colorpanel)
			.addComponent(thicknesspanel)
			.addComponent(drawPanel)
				
		);
		
		WBLayout.setVerticalGroup(WBLayout.createSequentialGroup()
			.addGroup(WBLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(colorpanel)
				.addComponent(thicknesspanel)
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

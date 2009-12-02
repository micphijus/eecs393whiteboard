package core.whiteboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ThicknessPanel extends JPanel{
	
	private CurrentDrawConfig curConfig;
	public ThicknessPanel(CurrentDrawConfig newConfig)
	{
		curConfig = newConfig;
		Float[] thicknesses = { 1.0f, 2.0f, 3.0f, 4.0f };
		final JDrawButton[] JBThicknesses = new JDrawButton[thicknesses.length];
		
		for (int i = 0; i < thicknesses.length; i++) {
			final int index = i;
			JBThicknesses[i] = new JDrawButton(curConfig, i);
			JBThicknesses[i].setSize(10, 10);
			JBThicknesses[i].setMaximumSize(new Dimension(25, 25));
			JBThicknesses[i].putClientProperty("thickness",thicknesses[i]);
			JBThicknesses[i].setFocusable(false);
			
			JBThicknesses[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setDrawThickness(Float.parseFloat(JBThicknesses[index].getClientProperty("thickness").toString()));
				}
			});
			this.add(JBThicknesses[i]);
		}
		JButton trial = new JButton();
		trial.setSize(25, 25);
		trial.setMaximumSize(new Dimension(25, 25));
		trial.setFocusable(false);
		trial.setBackground(Color.red);
		
		
		GroupLayout colorLayout = new GroupLayout(this);
		this.setLayout(colorLayout);
		colorLayout.setAutoCreateGaps(true);
		colorLayout.setAutoCreateContainerGaps(true);
		colorLayout.setHorizontalGroup(colorLayout.createSequentialGroup()	
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBThicknesses[0])
				.addComponent(JBThicknesses[2]))
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBThicknesses[1])
				.addComponent(JBThicknesses[3]))
		);
		
		colorLayout.setVerticalGroup(colorLayout.createSequentialGroup()
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBThicknesses[0])
				.addComponent(JBThicknesses[1]))
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBThicknesses[2])
				.addComponent(JBThicknesses[3]))
		);
	}
	/** 
	 * Applies the specified color as the Color used to draw
	 * @return void
	 */
	protected void setDrawThickness(float newThickness)
	{
		curConfig.SetThickness(newThickness);
	}
	
	class JDrawButton extends JButton{
		private CurrentDrawConfig curConfig;
		private float thickness;
		public JDrawButton(CurrentDrawConfig cdc, float f)
		{
			curConfig = cdc;
			thickness = f;
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g;
			
			BasicStroke wideStroke = new BasicStroke(thickness);
			
			g2.setStroke(wideStroke);
			g2.setColor(Color.black);
			g2.drawLine(5,5,30,20);
			
		}
	}
}


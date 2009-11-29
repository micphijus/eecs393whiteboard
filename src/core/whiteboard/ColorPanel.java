package core.whiteboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorPanel extends JPanel{
	
	private CurrentColor drawColor;
	public ColorPanel(CurrentColor mainColor)
	{
		drawColor = mainColor;
		Color[] colors = { Color.black, Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta };
		final JButton[] JBcolors = new JButton[8];
		
		for (int i = 0; i < colors.length; i++) {
			final int index = i;
			JBcolors[i] = new JButton();
			JBcolors[i].setSize(10, 10);
			JBcolors[i].setMaximumSize(new Dimension(25, 25));
			JBcolors[i].putClientProperty("color",colors[i]);
			JBcolors[i].setBackground((Color)JBcolors[i].getClientProperty("color"));
			JBcolors[i].setFocusable(false);
			
			JBcolors[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setDrawColor((Color)JBcolors[index].getClientProperty("color"));
				}
			});
			this.add(JBcolors[i]);
		}
		
		GroupLayout colorLayout = new GroupLayout(this);
		this.setLayout(colorLayout);
		colorLayout.setAutoCreateGaps(true);
		colorLayout.setAutoCreateContainerGaps(true);
		colorLayout.setHorizontalGroup(colorLayout.createSequentialGroup()	
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBcolors[0])
				.addComponent(JBcolors[2])
				.addComponent(JBcolors[4])
				.addComponent(JBcolors[6]))
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBcolors[1])
				.addComponent(JBcolors[3])
				.addComponent(JBcolors[5])
				.addComponent(JBcolors[7]))
		);
		
		colorLayout.setVerticalGroup(colorLayout.createSequentialGroup()
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBcolors[0])
				.addComponent(JBcolors[1]))
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBcolors[2])
				.addComponent(JBcolors[3]))
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBcolors[4])
				.addComponent(JBcolors[5]))
			.addGroup(colorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(JBcolors[6])
				.addComponent(JBcolors[7]))
		);
	}
	/** 
	 * Applies the specified color as the Color used to draw
	 * @return void
	 */
	protected void setDrawColor(Color newColor)
	{
		drawColor.Set(newColor);
	}
}

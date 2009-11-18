package core.whiteboard;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorPanel extends JPanel{
	
	private Color drawColor;
	public ColorPanel()
	{
		Color[] colors = { Color.black, Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta };
		final JButton[] JBcolors = new JButton[8];
		
		for (int i = 0; i < colors.length; i++) {
			final int index = i;
			JBcolors[i] = new JButton();
			JBcolors[i].putClientProperty("color",colors[i]);
			JBcolors[i].setBackground((Color)JBcolors[i].getClientProperty("color"));
			this.add(JBcolors[i]);
			
			JBcolors[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setDrawColor((Color)JBcolors[index].getClientProperty("color"));
				}
			});
			
		}
			
		
	}
	
	private void setDrawColor(Color newColor)
	{
		drawColor = newColor;
	}
}

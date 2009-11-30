package core.whiteboard;

import java.awt.Color;

public class CurrentColor {

	private Color color;
	public CurrentColor(Color firstColor)
	{	
		Set(firstColor);
	}
	public void Set(Color newColor){	color = newColor;	}
	public Color Get(){		return color;	}
}

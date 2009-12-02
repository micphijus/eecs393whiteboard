package core.whiteboard;

import java.awt.Color;

public class CurrentDrawConfig {

	private Color color;
	private float thickness;
	public CurrentDrawConfig(Color firstColor, float firstThickness)
	{	
		SetColor(firstColor);
		SetThickness(firstThickness);
	}
	public void SetColor(Color newColor){	color = newColor;	}
	public Color GetColor(){		return color;	}
	public void SetThickness(float newThickness){	thickness = newThickness;	}
	public float GetThickness(){		return thickness;	}
}

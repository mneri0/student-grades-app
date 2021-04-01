package application;

import javafx.scene.paint.Color;

public enum MyColor{
	c1(255, 26, 0, 1.0), c2(126, 0, 191, 1.0), c3(33, 30, 45, 1.0), 
	c4(0, 191, 172, 1.0), c5(50, 50, 50, 1.0), c6(190, 240, 30, 1.0),
	c7(11, 14, 19, 1.0), c8(45, 107, 86, 1.0), c9(187, 247, 131, 1.0),
	c10(77, 138, 136, 1.0);
	
	private Color c;
	
	MyColor(){
		// Random Default Color
		c = Color.rgb(0, 0, 1, 1.0);
	}
	MyColor(int red, int green, int blue, double opacity){
		c = Color.rgb(red, green, blue, opacity);
	}
	
	Color getColor(){
		return c;
	}
	
	void setColor(int red, int green, int blue, double opacity){
		c = Color.rgb(red, green, blue, opacity);
	}
	
	Color mixColor(Color first, Color second){
		Color mixedColor = new Color(first.getRed() * 0.5 + second.getRed() * 0.5, 
									first.getGreen() * 0.5 + second.getGreen() * 0.5, 
									first.getBlue() * 0.5 + second.getBlue() * 0.5, 
									first.getOpacity() * 0.5 + second.getOpacity() * 0.5);
		return mixedColor;
	}
}




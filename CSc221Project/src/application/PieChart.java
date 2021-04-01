package application;

import javafx.scene.paint.Color;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class PieChart{
	private  int n;
	
	public PieChart(){
		n = 0;
	}
	
	public PieChart(int n){
		if(n < 0){
			n = 0;
		}
		else if(n > 26){
			n = 26;
		}
		else{
			this.n = n;
		}
	}
	
	public int getN(){
		return n;
	}
	
	public Group draw(double width, double height, Map<String, String> histogramOfLetters, 
			double total){
		Group group = new Group();

		double startAngleAt = 90;
		
		Canvas c = new Canvas(width, height);
		GraphicsContext gc = c.getGraphicsContext2D();

		double textXCoord = 10;
		double textYCoord = 10;
		for(Map.Entry<String, String> entry : histogramOfLetters.entrySet()){
			System.out.println("key = " + entry.getKey() + " value ="  + entry.getValue());

			// Color for each pie slice
			Color color = new Color(Math.random(), Math.random(), Math.random(), Math.random());
			// This is for the already determined scene (size of 600 x 600)
	        Arc arc = new Arc();
	        arc.setCenterX((500 /2) - (100 / 2)); 
	        arc.setCenterY((500 /2) - (100 / 2)); 
	        arc.setRadiusX(150); 
	        arc.setRadiusY(150);
	        arc.setTranslateX(100); 
	        arc.setTranslateY(100); 
	        arc.setType(ArcType.ROUND); 
	        
	        // Start the next pie slice where we left off and add different colors
	        arc.setStartAngle(startAngleAt); 
	        arc.setLength(Math.toDegrees((Double.parseDouble(entry.getValue()) * 2 * Math.PI)));
	        arc.setFill(color);
	        
	        // Update our starting angle
	        startAngleAt += Math.toDegrees((Double.parseDouble(entry.getValue()) * 2 * Math.PI));	     
	        
	        // Add this pie slice to the pie graph
	        group.getChildren().add(arc);
	        
	        // Add the pie slice's information
	        gc.setFill(color);		
			textYCoord = textYCoord + 10;
			String num = Integer.toString((int)(Double.parseDouble(entry.getValue()) * total));
			gc.fillText(entry.getValue() + ", " + entry.getKey() + ", " + num,
					textXCoord, textYCoord);

			System.out.println("x =" + textXCoord + " y =" + textYCoord);
			
		}
		
		group.getChildren().add(c);
		return group;
	}
}

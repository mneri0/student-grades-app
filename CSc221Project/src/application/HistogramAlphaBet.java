package application;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Scene;
import javafx.stage.Stage;

public class HistogramAlphaBet extends Application{
	private static Scanner input;
	private static int n;
//	private static Map<String, String> mapForPieChart;
	
	public static void incrementFrequency(Map<Character, Integer> f, Character ch){
		f.putIfAbsent(ch, 0);
		f.put(ch, f.get(ch) + 1);
	}
	
	private static void openFile(){
		try{
			//input = new Scanner(new File("C:\\Users\\Desktop\\Emma.txt"));
			//input = new Scanner(new File(‎⁨"//Users//mike//Desktop//Emma.txt⁩"));
			input = new Scanner(new File("Emma.txt"));
		}
		catch(IOException ioe){
			System.out.println(ioe.toString());
			System.out.println("ERROR: could not read file");
		}
	}
	
	private static void readFile(){
			Map<Character, Integer> f = new TreeMap<Character, Integer>();
			String text = "";
			double total = 0;
			while(input.hasNext()){
				String s = input.next();
				text+=s;
			}
			text = text.replaceAll("[^a-zA-Z]","").toLowerCase();
			for(int i = 0; i < text.length(); i++){
				Character ch = text.charAt(i);
				incrementFrequency(f, ch);
			}
			
			Map<Integer, Character> reversedF = new TreeMap<Integer, Character>();
			for(Map.Entry<Character, Integer> entry : f.entrySet()){
				reversedF.put(entry.getValue(), entry.getKey());
				total+= entry.getValue();
			}
			
			NavigableMap<Integer, Character> descendingF = ((TreeMap<Integer, Character>) reversedF).descendingMap();
			
			System.out.println("Total = " + total);
			int remainder = 0;
			DecimalFormat dF = new DecimalFormat("#.####");
			dF.setRoundingMode(RoundingMode.CEILING);
			
			Map<String, String> probabilitiesOfLetter = new TreeMap<String, String>();
			for(Map.Entry<Integer, Character> entry : descendingF.entrySet()){
				if(n > 0){
					probabilitiesOfLetter.put(dF.format(entry.getKey() / total), entry.getValue().toString());
					remainder+= entry.getKey();
					n--;
				}
				else{
					probabilitiesOfLetter.put(dF.format((total - remainder) / total), "All other letters");
					break;
				}
			}
			
			for(Map.Entry<String, String> entry : probabilitiesOfLetter.entrySet()){
				System.out.println("Probability = " + entry.getKey() + ", Letter = " + entry.getValue());
			}
			
			//mapForPieChart = new TreeMap<String, String>(probabilitiesOfLetter);
	}
	
	private static void closeFile(){
		if(input != null)
			input.close();
	}
	
    @Override
    public void start(Stage stage){ 
        //PieChart p = new PieChart(n);
        //Group group = p.draw(600, 600, mapForPieChart);
        
        //Scene sc = new Scene(group, 600, 600);

        stage.setTitle("Pie Chart of n most frequent letters");
        //stage.setScene(sc);
        stage.show();
    }

	public static void main(String[] args){
		System.out.println("Enter the n variable to start the PieChart generation");
		input = new Scanner(System.in);
		n = input.nextInt();
		openFile();
		readFile();
		closeFile();
		
		launch(args);
	}
	
	// Didn't work well (suppose to be listed around the circle)
	/*
	textXCoord = Math.abs((textXCoord * (Math.cos(Double.parseDouble(entry.getKey()) * 2 * Math.PI)))) - 
			Math.abs((textYCoord * Math.sin((Double.parseDouble(entry.getKey()) * 2 * Math.PI))));
	textYCoord = Math.abs((textYCoord * (Math.cos(Double.parseDouble(entry.getKey()) * 2 * Math.PI)))) + 
			Math.abs((textXCoord * Math.sin((Double.parseDouble(entry.getKey()) * 2 * Math.PI))));
	gc.fillText(entry.getKey() + ", " + entry.getValue(), textXCoord + 300, textYCoord + 300);
	*/
}

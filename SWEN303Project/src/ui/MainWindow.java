package ui;


import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.Country;
import backend.Region;

import canvas.ScatterCanvas;
import canvas.StatPoint;

public class MainWindow extends JFrame{
	ScatterCanvas scatter;
	JPanel timeline;
	//A map for matching a country code to a 
	HashMap<String, Country> countries = new HashMap<String,Country>();
	
	public MainWindow(String s){
		super(s);		
		scatter = new ScatterCanvas();
		setLayout(new FlowLayout());
		add(scatter);
		add(new JLabel("StatYou?"));
		pack();
		setVisible(true);
		readCountries();
		readFile(new File("res/woman.txt"));
	}

	public void setScatter(ScatterCanvas s){
		scatter = s;
	}
	
	private void readFile(File f){
		try {
			Scanner sc = new Scanner(f);
			String fLine = sc.nextLine();
			
			while (sc.hasNext()){
				String[] l = sc.nextLine().split(":");
				scatter.addPoint(new StatPoint(Double.valueOf(l[0]), Double.valueOf(l[1]), l[2]));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	private void readCountries() {
		try {
			Scanner sc = new Scanner(new File("res/countries.txt"));
			while (sc.hasNextLine()){
				String[] split = sc.nextLine().split(":");
				countries.put(split[1], new Country(split[0], split[1], Region.fromString(split[2])));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open countrycodes.txt");
			e.printStackTrace();
		}
	}
}

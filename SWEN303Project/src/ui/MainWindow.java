package ui;


import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import canvas.ScatterCanvas;

public class MainWindow extends JFrame{
	JPanel scatter;
	JPanel timeline;
	HashMap<String, String> countries = new HashMap<String,String>();
	
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
		
	}
	private void readCountries() {
		try {
			Scanner sc = new Scanner(new File("res/countrycodes.txt"));
			while (sc.hasNextLine()){
				String[] split = sc.nextLine().split(":");
				countries.put(split[0], split[1]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open countrycodes.txt");
			e.printStackTrace();
		}
	}
}

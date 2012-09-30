package ui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import backend.Country;
import backend.Region;
import canvas.CountryPoint;
import canvas.ScatterCanvas;

public class MainWindow extends JFrame{
	ScatterCanvas scatter;
	JPanel controlPanel;
	JPanel presPanel;
	JPanel showPanel;
	JButton regNatButton;
	JTextField fileBox;
	//A map for matching a country code to a 
	public static HashMap<String, Country> countries = new HashMap<String,Country>();
	public static JLabel status = new JLabel("Waiting...");
	
	public MainWindow(String s){
		super(s);		
		scatter = new ScatterCanvas();
		setLayout(new MigLayout());
		add(scatter, "id plot, pos 0 0, pad 10 10 10 10");
		add(status, "pos plot.x plot.y2 plot.x2 null");
		scatter.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		controlPanel =new JPanel();
		controlPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		controlPanel.setLayout(new MigLayout("wrap, gap 2 5","[grow,left][right]","[][]"));
		controlPanel.add(new JLabel("Load Stats"),"wrap");
		fileBox = new JTextField("res/woman.txt",25);
		controlPanel.add(fileBox,"grow, span 2");
		JButton browse, open, makePres, showPres, quit;
		controlPanel.add(browse = new JButton("Browse"),"skip, split 2");
		controlPanel.add(open = new JButton("Open"));
		controlPanel.add(regNatButton = new JButton("Regional"), "span 2, grow");
		controlPanel.add(makePres = new JButton("Build Presentation"),"gaptop 300, span 2, grow");
		controlPanel.add(showPres = new JButton("Show Presentation"),"span 2, grow");
		controlPanel.add(quit = new JButton("Quit"),"span 2, grow");
		ButtonListener b = new ButtonListener();
		browse.addActionListener(b);
		open.addActionListener(b);
		makePres.addActionListener(b);
		showPres.addActionListener(b);
		quit.addActionListener(b);
		regNatButton.addActionListener(b);
		add(controlPanel, "east, pos plot.x2 plot.y null plot.y2");
		pack();
		setVisible(true);
		readCountries();
		readFile(new File("res/woman.txt"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setScatter(ScatterCanvas s){
		scatter = s;
	}
	
	public void readFile(File f){
		try {
			Scanner sc = new Scanner(f);
			String[] header = sc.nextLine().split(",");
			scatter.setLabels(header[0],header[1]);
			while (sc.hasNext()){
				String[] l = sc.nextLine().split(":");
				scatter.addPoint(new CountryPoint(Double.valueOf(l[1]), Double.valueOf(l[2]), countries.get(l[0])));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scatter.repaint();
		
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
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String goal = e.getActionCommand();
			switch(goal){
			case "Browse":
				JFileChooser fileChooser = new JFileChooser(fileBox.getText());
				if(fileChooser.showOpenDialog(MainWindow.this)== JFileChooser.APPROVE_OPTION){
					fileBox.setText(fileChooser.getSelectedFile().toString());
				}
				break;
			case "Regional":
				groupData();
				regNatButton.setText("National");
				revalidate();
				break;
			case "National":
				breakUpData();
				regNatButton.setText("Regional");
				revalidate();
				break;
			case "Quit":
				System.exit(0);
				break;
			case "Open":
				scatter.reset();
				readFile(new File(fileBox.getText()));
				break;
			case "Show Presentation":
				JFileChooser presfileChooser = new JFileChooser(fileBox.getText());
				if(presfileChooser.showOpenDialog(MainWindow.this)== JFileChooser.APPROVE_OPTION){
					startPresentation(presfileChooser.getSelectedFile());
				} else {
					return;
				}
				break;
			case "Build Presentation":
				JFileChooser newfileChooser = new JFileChooser(fileBox.getText());
				if(newfileChooser.showSaveDialog(MainWindow.this)== JFileChooser.APPROVE_OPTION){
					makePresentation(newfileChooser.getSelectedFile());
				} else {
					return;
				}
				break;
			}
		}

		private void breakUpData() {
			
		}

		private void groupData() {
			
		}

		private void makePresentation(File selectedFile) {
			// TODO Auto-generated method stub
			
		}

		private void startPresentation(File selectedFile) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}

package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import canvas.StatPoint;
import canvas.Vector;

public class MainWindow extends JFrame {
	ScatterCanvas scatter;
	JPanel controlPanel;
	JPanel presPanel;
	JPanel showPanel;
	JButton regNatButton;
	JButton makePres, showPres, browse, open, quit;
	JTextField fileBox;
	// A map for matching a country code to a
	public static HashMap<String, Country> countries = new HashMap<String, Country>();
	public static JLabel status = new JLabel("...");

	public MainWindow(String s) {
		super(s);
		scatter = new ScatterCanvas();
		setLayout(new MigLayout());
		add(scatter, "id plot, pos 0 0, pad 10 10 10 10");
		add(status, "pos plot.x plot.y2 plot.x2 null");
		scatter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		controlPanel.setLayout(new MigLayout("wrap, gap 2 5",
				"[grow,left][right]", "[][]"));
		controlPanel.add(new JLabel("Load Stats"), "wrap");
		fileBox = new JTextField("res/woman.txt", 25);
		controlPanel.add(fileBox, "grow, span 2");
		controlPanel.add(browse = new JButton("Browse"), "skip, split 2");
		controlPanel.add(open = new JButton("Open"));
		controlPanel
				.add(regNatButton = new JButton("Regional"), "span 2, grow");
		controlPanel.add(makePres = new JButton("Build Presentation"),
				"gaptop 300, span 2, grow");
		controlPanel.add(showPres = new JButton("Show Presentation"),
				"span 2, grow");
		controlPanel.add(quit = new JButton("Quit"), "span 2, grow");
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

	public void setScatter(ScatterCanvas s) {
		remove(scatter);
		scatter = s;
		add(scatter, "id plot, pos 0 0, pad 10 10 10 10");
	}

	public void readFile(File f) {
		try {
			Scanner sc = new Scanner(f);
			String[] header = sc.nextLine().split(",");
			scatter.setLabels(header[0], header[1]);
			while (sc.hasNext()) {
				String[] l = sc.nextLine().split(":");
				scatter.addPoint(new CountryPoint(Double.valueOf(l[1]), Double
						.valueOf(l[2]), countries.get(l[0])));
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
			while (sc.hasNextLine()) {
				String[] split = sc.nextLine().split(":");
				countries.put(
						split[1],
						new Country(split[0], split[1], Region
								.fromString(split[2])));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open countrycodes.txt");
			e.printStackTrace();
		}
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String goal = e.getActionCommand();
			switch (goal) {
			case "Browse":
				JFileChooser fileChooser = new JFileChooser(fileBox.getText());
				if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
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
				JFileChooser presfileChooser = new JFileChooser(
						fileBox.getText());
				if (presfileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
					startPresentation(presfileChooser.getSelectedFile());
				} else {
					return;
				}
				break;
			case "Build Presentation":
				JFileChooser newfileChooser = new JFileChooser(
						fileBox.getText());
				if (newfileChooser.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
					makePresentation(newfileChooser.getSelectedFile());
				} else {
					return;
				}
				break;
			case "Return":
				controlPanel = new JPanel();
				controlPanel.setBorder(BorderFactory
						.createTitledBorder("Options"));
				controlPanel.setLayout(new MigLayout("wrap, gap 2 5",
						"[grow,left][right]", "[][]"));
				controlPanel.add(new JLabel("Load Stats"), "wrap");
				fileBox = new JTextField("res/woman.txt", 25);
				controlPanel.add(fileBox, "grow, span 2");
				controlPanel.add(browse, "skip, split 2");
				controlPanel.add(open);
				controlPanel.add(regNatButton, "span 2, grow");
				controlPanel.add(makePres, "gaptop 300, span 2, grow");
				controlPanel.add(showPres, "span 2, grow");
				controlPanel.add(quit, "span 2, grow");
				add(controlPanel, "east, pos plot.x2 plot.y null plot.y2");
				break;

			}
		}

		private void breakUpData() {
			setScatter(new ScatterCanvas());
			readFile(new File("res/woman.txt"));
		}

		private void groupData() {
			Map<Region, List<StatPoint>> regionPoints = new HashMap<Region, List<StatPoint>>();
			List<StatPoint> endList = new ArrayList<StatPoint>();
			for (Region r : Region.values()) {
				regionPoints.put(r, new ArrayList<StatPoint>());
			}
			for (StatPoint p : scatter.getPoints()) {
				Country c = null;
				if (p instanceof CountryPoint) {
					c = ((CountryPoint) p).getCountry();
				} else {
					c = countries.get(p.getName());
				}
				System.out.println(c);
				if (c == null)
					continue;
				regionPoints.get(c.getRegion()).add(p);
			}
			for (Region r : regionPoints.keySet()) {
				int xSum = 0, ySum = 0, count = 0;
				for (StatPoint p : regionPoints.get(r)) {
					xSum += p.getX();
					ySum += p.getY();
					count++;
				}
				if (count == 0)
					continue;
				StatPoint result = new StatPoint(xSum / count, ySum / count,
						r.toString());
				endList.add(result);
				for (StatPoint p : regionPoints.get(r)) {
					p.setDest(new Vector(p, result));
					new GrouperThread(regionPoints.get(r), result).start();
				}
			}
		}

		private void makePresentation(File selectedFile) {
			remove(controlPanel);
			presPanel = new JPanel();
			presPanel.setBorder(BorderFactory
					.createTitledBorder("Presentation"));
			presPanel.setLayout(new MigLayout("wrap, gap 2 5",
					"[grow,left][right]", "[][]"));
			presPanel.add(new JLabel("Load Stats"), "wrap");
			presPanel.add(fileBox, "grow, span 2");
			presPanel.add(browse, "skip, split 2");
			presPanel.add(open);
			presPanel.add(regNatButton, "span 2, grow");
			JButton ret = new JButton("Return");
			presPanel.add(ret, "gaptop 300, span 2, grow");
			presPanel.add(showPres, "span 2, grow");
			add(presPanel, "east, pos plot.x2 plot.y null plot.y2");
			revalidate();
		}

		private void startPresentation(File selectedFile) {
			final JFrame pres = new JFrame();
			pres.setUndecorated(true);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			pres.setBounds(0, 0, screenSize.width, screenSize.height);
			pres.setLayout(new GridLayout());
			pres.add(scatter);
//			scatter = new ScatterCanvas(screenSize);
			pres.setVisible(true);
			pres.revalidate();
			new Thread() {
				public void run() {
					try {
						System.out.println("Reading file 1");
						readFile(new File("res/woman.txt"));
						Thread.sleep(10000);
						System.out.println("and File 2");
						readFile(new File("res/trial.txt"));
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					scatter = new ScatterCanvas();
					add(scatter, "id plot, pos 0 0, pad 10 10 10 10");
					scatter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
					pres.dispose();
					readFile(new File("res/woman.txt"));
				}
			}.start();
		}

	}

	private class GrouperThread extends Thread {
		List<StatPoint> points;
		StatPoint end;

		public GrouperThread(List<StatPoint> p, StatPoint result) {
			super();
			points = p;
			end = result;
		}

		public void run() {
			while (!points.get(points.size() - 1).atEnd()) {
				for (StatPoint p : points) {
					if (!p.atEnd()) {
						p.moveToward(20);
					}
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scatter.removePoints(points);
			scatter.addPoint(end);
		}
	}
}

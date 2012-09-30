package canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

public class ScatterCanvas extends JPanel {
	// The point list
	List<StatPoint> points = new ArrayList<StatPoint>();
	HashMap<String, CountryPoint> pointsByCountry = new HashMap<String, CountryPoint>();
	HashMap<Dimension, StatPoint> pointsByLocation = new HashMap<>();
	// Extra drawable items
	List<Drawable> drawables = new ArrayList<Drawable>();
	double xMaxV = -Double.MAX_VALUE;
	double yMaxV = -Double.MAX_VALUE;
	double xMinV = 0;
	double yMinV = 0;
	Thread redraw;
	Dimension scaleFactor;
	Dimension offset;
	String xLabel = "", yLabel = "";

	Dimension size = new Dimension(800, 600);

	public ScatterCanvas() {
		super();
		setPreferredSize(size);
		// setMaximumSize(size);
		setMinimumSize(size);
		setBackground(Color.white);
		addMouseMotionListener(new CanvasMouseListener());
		requestFocusInWindow();
		redraw = new RedrawThread();
		redraw.start();
	}

	public void addPoint(CountryPoint p) {
		points.add(p);
		pointsByCountry.put(p.getCountry().getCode(), p);
	}

	public void addPoint(StatPoint p) {
		points.add(p);
	}

	public void setLabels(String xLabel, String yLabel) {
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Stroke bland = new BasicStroke(1.5f);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// Paint the axes
		calculateAxes();
		if (xMaxV == xMinV || yMaxV == yMinV) {
			return;
		}
		g.drawString(xLabel, offset.width + 10, getHeight() / 4);
		g.drawString(yLabel, getWidth() / 4 * 3, getHeight() - offset.height
				- 10);
		for (StatPoint p : new ArrayList<StatPoint>(points)) {
			p.drawLabel(g, getHeight());
		}
		g2d.scale(1, -1);
		g2d.translate(0, -getHeight());

		// System.out.printf("xMaxV=%f xMinV=%f yMaxV=%f yMinV=%f\n",
		// xMaxV,xMinV, yMaxV, yMinV);
		// System.out.printf("offset.width = %d, offset.height = %d\n",offset.width,
		// offset.height);
		g.drawLine(offset.width, -offset.height, offset.width, offset.height
				+ this.getHeight());
		g.drawLine(offset.width, offset.height, offset.width + this.getWidth(),
				offset.height);
		for (StatPoint p : new ArrayList<StatPoint>(points)) {
			pointsByLocation.remove(p.getLocation());
			p.calculate(scaleFactor, offset);
			pointsByLocation.put(p.getLocation(), p);
			p.drawSelf(g);
			g2d.setStroke(bland);
		}
		for (Drawable d : drawables) {
			d.drawSelf(g);
			System.out.println("drawing " + d);
		}
	}

	public Dimension valueToCoord(Dimension val) {
		return new Dimension(val);
	}

	private void calculateAxes() {
		for (StatPoint p : new ArrayList<StatPoint>(points)) {
			double x = p.getXVal();
			double y = p.getYVal();
			xMaxV = (x > xMaxV) ? x : xMaxV;
			yMaxV = (y > yMaxV) ? y : yMaxV;
			xMinV = (x < xMinV) ? x : xMinV;
			yMinV = (y < yMinV) ? y : yMinV;
		}

		scaleFactor = new Dimension(
				(int) Math.round((getSize().getWidth() - 10) / (xMaxV - xMinV)),
				(int) Math
						.round((getSize().getHeight() - 10) / (yMaxV - yMinV)));
		offset = new Dimension((int) (scaleFactor.getWidth() * xMinV) + 10,
				(int) (scaleFactor.getHeight() * -yMinV) + 10);
	}

	private class CanvasMouseListener extends MouseMotionAdapter {
		private List<StatPoint> over = new ArrayList<>();
	@Override
		public void mouseMoved(MouseEvent e) {
			List<StatPoint> oldOver = new ArrayList<>(over);
			over = new ArrayList<>();
			int x, y;
			x = e.getX();
			y = getHeight() - e.getY();
			for (Dimension d : pointsByLocation.keySet()) {
				if (Math.abs(d.height - y) < 5 && Math.abs(d.width - x) < 5) {
					pointsByLocation.get(d).setHovered(true);
					over.add(pointsByLocation.get(d));
				}
			}
			for (StatPoint s:oldOver){
				if(!over.contains(s)){
					s.startFade();
				}
			}

		}

	}
	private class RedrawThread extends Thread{
		public void run(){
			while(true){
				repaint();
				try {
					this.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

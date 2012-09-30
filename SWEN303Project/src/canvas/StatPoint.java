package canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StatPoint extends Point implements Drawable {
	// A stat point is a drawable object that has a position, a label and when
	// necessary a movement vector
	public static final int POINT_SIZE = 6;

	private Vector dest;
	private String label;
	private double xV, yV;
	private boolean enabled = false;
	private boolean hovered = false;
	private Thread fadeThread = null;
	protected Color highlightColor = Color.red;
	protected Color normalColor = Color.black;
	private int strokeSize = 1;
	private Dimension location = new Dimension();

	public StatPoint(int x, int y) {
		super(x, y);
		dest = null;
		label = null;
		enabled = true;

	}

	public StatPoint(StatPoint old) {
		super(old.x, old.y);
		dest = old.dest;
		label = old.label;
		xV = old.xV;
		yV = old.yV;
		enabled = old.enabled;
	}

	public StatPoint(StatPoint old, String label) {
		super(old.x, old.y);
		dest = old.dest;
		this.label = label;
		xV = old.xV;
		yV = old.yV;
		enabled = old.enabled;
	}

	public StatPoint(double xVal, double yVal) {
		super(0, 0);
		xV = xVal;
		yV = yVal;
		dest = null;
		label = null;
	}

	public StatPoint(double xVal, double yVal, String label) {
		super(0, 0);
		xV = xVal;
		yV = yVal;
		dest = null;
		this.label = label;
	}

	public void calculate(Dimension scaleFactor, Dimension offset) {
		setX((int) (Math.round(xV * scaleFactor.getWidth()) + offset.getWidth()));
		setY((int) (Math.round(yV * scaleFactor.getHeight()) + offset
				.getHeight()));
		location.height = y;
		location.width = x;
		enabled = true;
	}

	@Override
	public boolean drawSelf(Graphics g) {
		if (enabled) {
			if (hovered) {
				((Graphics2D) g).setStroke(new BasicStroke(strokeSize));
				g.setColor(highlightColor);
			} else
				g.setColor(normalColor);
			g.drawOval(x - POINT_SIZE / 2, y - POINT_SIZE / 2, POINT_SIZE,
					POINT_SIZE);
		}
		return enabled;
	}

	public boolean drawLabel(Graphics g, int height) {
		if (hovered) {
			g.setColor(Color.black);
			g.drawString(label, x - 10, height - y - 10);
		}
		return hovered;
	}
	
	public String getName(){
		return label;
	}

	public Vector getDest() {
		return dest;
	}

	public void setDest(Vector dest) {
		this.dest = dest;
	}

	public double getXVal() {
		return xV;
	}

	public void setXVal(double xV) {
		this.xV = xV;
	}

	public double getYVal() {
		return yV;
	}

	public void setYVal(double yV) {
		this.yV = yV;
	}

	public Dimension getLocation() {
		return location;
	}

	public void setHovered(boolean b) {
		hovered = b;
		highlightColor = Color.red;
		strokeSize = 3;
		if (false){
			startFade();
		}
	}
	
	public void startFade(){
		if (fadeThread == null) {
			(fadeThread = new FadeThread()).start();
		}
	}

	private class FadeThread extends Thread {
		public void run() {
			while (!highlightColor.equals(Color.BLACK) && strokeSize != 1) {
				highlightColor = highlightColor.darker();
				strokeSize -= .2;
				try {
					sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			hovered = false;
			fadeThread = null;
		}
	}
	private class AnimateThread extends Thread {
		public void run() {
			while (!highlightColor.equals(Color.BLACK)) {
				highlightColor = highlightColor.darker();
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			hovered = false;
			fadeThread = null;
		}
	}

}

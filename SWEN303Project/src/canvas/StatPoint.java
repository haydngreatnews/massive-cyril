package canvas;

import java.awt.Dimension;
import java.awt.Graphics;

public class StatPoint extends Point implements Drawable {
	// A stat point is a drawable object that has a position, a label and when
	// necessary a movement vector
	public static final int POINT_SIZE = 4;

	private Vector dest;
	private String label;
	private double xV, yV;
	private boolean enabled = false;

//	public StatPoint(int x, int y) {
//		super(x, y);
//		dest = null;
//		label = null;
//		enabled = true;
//	}
	
	public StatPoint(StatPoint old){
		super(old.x, old.y);
		dest = old.dest;
		label = old.label;
		xV = old.xV;
		yV = old.yV;
		enabled = old.enabled;
	}
	
	public StatPoint(StatPoint old, String label){
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
		setY((int) (Math.round(yV * scaleFactor.getHeight()) + offset.getHeight()));
		enabled = true;
	}

	@Override
	public boolean drawSelf(Graphics g) {
		if (enabled)
			g.drawOval(x - POINT_SIZE / 2, y - POINT_SIZE / 2, POINT_SIZE,
					POINT_SIZE);
		return enabled;
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
	
}

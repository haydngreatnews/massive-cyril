package canvas;

import java.awt.Graphics;

public class StatPoint extends Point implements Drawable {
	// A stat point is a drawable object that has a position, a label and when
	// necessary a movement vector
	public static final int POINT_SIZE = 4;
	
	private Vector dest;
	private String label;

	public StatPoint(int x, int y) {
		super(x, y);
		dest = null;
		label = null;
	}

	@Override
	public boolean drawSelf(Graphics g) {
		g.drawOval(x-POINT_SIZE/2, y-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
		return true;
	}
}

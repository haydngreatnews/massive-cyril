package canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ScatterCanvas extends JPanel {
	// The point list
	List<StatPoint> points = new ArrayList<StatPoint>();
	// Extra drawable items
	List<Drawable> drawables = new ArrayList<Drawable>();
	double xMaxV = -Double.MAX_VALUE;
	double yMaxV = -Double.MAX_VALUE;
	double xMinV = 0;
	double yMinV = 0;
	Dimension scaleFactor;
	Dimension offset;
	String xLabel, yLabel;

	Dimension size = new Dimension(800, 600);

	public ScatterCanvas() {
		super();
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	
	public void addPoint(StatPoint p){
		points.add(p);
	}
	
	public void setLabels(String xLabel, String yLabel){
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Paint the axes
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(1, -1);
		g2d.translate(0,-getHeight());
		calculateAxes();
		if (xMaxV == xMinV || yMaxV == yMinV) {
			return;
		}
		g.drawLine(offset.width, offset.height, offset.width, offset.height+this.getHeight());
		g.drawLine(offset.width, offset.height, offset.width+this.getWidth(), offset.height);
		for (StatPoint p : points) {
			p.calculate(scaleFactor, offset);
			p.drawSelf(g);
		}
		for (Drawable d : drawables) {
			d.drawSelf(g);
		}
	}

	public Dimension valueToCoord(Dimension val) {
		return new Dimension(val);
	}

	private void calculateAxes() {
		for (StatPoint p : points) {
			double x = p.getXVal();
			double y = p.getYVal();
			xMaxV = (x > xMaxV) ? x : xMaxV;
			yMaxV = (y > yMaxV) ? y : yMaxV;
			xMinV = (x < xMinV) ? x : xMinV;
			yMinV = (y < yMinV) ? y : yMinV;
		}

		scaleFactor = new Dimension((int) Math.round((getSize().getWidth()-10)
				/ (xMaxV - xMinV)), (int) Math.round((getSize().getHeight()-10)
				/ (yMaxV - yMinV)));
		offset = new Dimension((int) (scaleFactor.getWidth() * xMinV) +10,
				(int) (scaleFactor.getHeight() * yMinV)+10);
	}

}

package canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

public class ScatterCanvas extends JPanel {
	// The point list
	List<StatPoint> points = new ArrayList<StatPoint>();
	HashMap<String, StatPoint> pointsByCountry = new HashMap<String, StatPoint>();
	HashMap<Dimension, StatPoint> pointsByLocation = new HashMap<>();
	// Extra drawable items
	List<Drawable> drawables = new ArrayList<Drawable>();
	double xMaxV = -Double.MAX_VALUE;
	double yMaxV = -Double.MAX_VALUE;
	double xMinV = 0;
	double yMinV = 0;
	Dimension scaleFactor;
	Dimension offset;
	String xLabel="", yLabel="";

	Dimension size = new Dimension(800, 600);

	public ScatterCanvas() {
		super();
		setPreferredSize(size);
		// setMaximumSize(size);
		setMinimumSize(size);
		setBackground(Color.white);
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
		// Paint the axes
		calculateAxes();
		if (xMaxV == xMinV || yMaxV == yMinV) {
			return;
		}
		//g2d.rotate(-Math.PI/2);
		g.drawString(xLabel, offset.width+10, getHeight()/4);
		//g2d.rotate(Math.PI/2);
		g.drawString(yLabel, getWidth()/4*3, getHeight()-offset.height -10);

		g2d.scale(1, -1);
		g2d.translate(0, -getHeight());

//		System.out.printf("xMaxV=%f xMinV=%f yMaxV=%f yMinV=%f\n", xMaxV,xMinV, yMaxV, yMinV);
//		System.out.printf("offset.width = %d, offset.height = %d\n",offset.width, offset.height);
		g.drawLine(offset.width, -offset.height, offset.width, offset.height+ this.getHeight());
		g.drawLine(offset.width, offset.height, offset.width + this.getWidth(),	offset.height);
		for (StatPoint p : new ArrayList<StatPoint>(points)) {
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
				(int) Math.round((getSize().getHeight() - 10) / (yMaxV - yMinV)));
		System.out.printf("(yMaxV - yMinV)=%f, (xMaxV - xMinV)=%f",(yMaxV - yMinV),(xMaxV - xMinV));
		offset = new Dimension((int) (scaleFactor.getWidth() * xMinV) + 10,
				(int) (scaleFactor.getHeight() * -yMinV) + 10);
	}
	private class CanvasMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}


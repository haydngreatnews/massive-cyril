package canvas;

import java.awt.Dimension;

public class Vector {
	//A vector has a start point and an end point, and is available for operations on the line between them
	private Point start, end;
	public Vector(Point s, Point e){
		start = s;
		end = e;
	}
	public Vector(Point s, int xEnd, int yEnd){
		start = s;
		end = new Point(xEnd, yEnd);
	}
	
	public Dimension divBy(int d){
		return new Dimension((end.getX()-start.getX())/d, (end.getY()-start.getY())/d);
	}
	public Point getEnd(){
		return end;
	}
}

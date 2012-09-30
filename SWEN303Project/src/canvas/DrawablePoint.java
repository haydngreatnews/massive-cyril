package canvas;

import java.awt.Color;
import java.awt.Graphics;

public class DrawablePoint extends Point implements Drawable {

	public DrawablePoint(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean drawSelf(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(x-1,y-1, 2, 2);
		return true;
	}

}

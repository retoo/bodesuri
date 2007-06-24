package ui.geteiltes;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

public class BLabel extends JLabel {
	private final int dxFinal;
	private final int dyFinal;
	private final Point relativerMittelpunkt;
	private Point absoluterMittelpunkt;

	public BLabel(Icon icon) {
		this(icon, 0, 0);
	}

	public BLabel(Icon icon, int dx, int dy) {
		super(icon);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();

		this.dxFinal = (width / 2) + dx;
		this.dyFinal = (height / 2) + dy;

		setSize(width, height); /* TONIGHT WE SWING IN HELL */

		this.absoluterMittelpunkt = getLocation();
		this.relativerMittelpunkt = new Point(width / 2, height /2);
	}

	public BLabel(Icon icon, Point p) {
	    this(icon, 0, 0);
	    zentriereAuf(p);
    }

	public void zentriereAuf(Point position) {
		absoluterMittelpunkt = position;
		setLocation(position.x - dxFinal, position.y - dyFinal);
	}


	private void zentriereAuf(int x, int y) {
		absoluterMittelpunkt = new Point(x, y);
		setLocation(x - dxFinal, y - dyFinal);
    }

	public void bewegeNach(Point ziel) {
		Point start = absoluterMittelpunkt;

		double distanz = start.distance(ziel);

		int steps = (int) Math.round(distanz / 10);
		int dx = ziel.x - start.x;
		int dy = ziel.y - start.y;

		double stepX = dx / steps;
		double stepY = dy / steps;

		for (int i = 0; i < steps; i++) {
			int curX = start.x + (int) Math.round(i * stepX);
			int curY = start.y + (int) Math.round(i * stepY);

			zentriereAuf(curX, curY);
			try {
	            Thread.sleep(10);
            } catch (InterruptedException e) {
            }
		}

		zentriereAuf(ziel);
	}

	public Point getRelativerMittelpunkt() {
		return relativerMittelpunkt;
	}
}

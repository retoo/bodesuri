package ui.erweiterungen;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

public class BLabel extends JLabel {
	private final int dxFinal;
	private final int dyFinal;
	private final Point mittelpunkt;

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

		this.mittelpunkt = new Point(width / 2, height /2);
	}

	public BLabel(Icon icon, Point p) {
	    this(icon, 0, 0);
	    zentriereAuf(p);
    }

	public void zentriereAuf(Point position) {
		setLocation(position.x - dxFinal, position.y - dyFinal);
	}

	public Point getMittelpunkt() {
		return mittelpunkt;
	}
}

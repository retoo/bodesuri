package ui.erweiterungen;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

public class BLabel extends JLabel {
	private int dx;
	private int dy;

	public BLabel(Icon icon) {
		this(icon, 0, 0);
	}

	public BLabel(Icon icon, int dx, int dy) {
		super(icon);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();

		this.dx = (width / 2) + dx;
		this.dy = (height / 2) + dy;

		setSize(width, height); /* TONIGHT WE SWING IN HELL */
	}

	public void zentriereAuf(Point position) {
		setLocation(position.x - dx, position.y - dy);
	}
}

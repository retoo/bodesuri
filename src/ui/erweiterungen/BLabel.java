package ui.erweiterungen;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

public class BLabel extends JLabel {
	private int dx;
	private int dy;

	public BLabel(Icon icon) {
		this(icon, icon.getIconWidth() / 2, icon.getIconHeight() / 2);
	}

	public BLabel(Icon icon, int dx, int dy) {
		super(icon);
		this.dx = dx;
		this.dy = dy;
		setSize(icon.getIconWidth(), icon.getIconHeight());
	}

	public void zentriereAuf(Point position) {
		setLocation(position.x - dx, position.y - dy);
	}
}

package ui.erweiterungen;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

public class BLabel extends JLabel {
	private final int dxFinal;
	private final int dyFinal;

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
	}

	public void zentriereAuf(Point position) {
		setLocation(position.x - dxFinal, position.y - dyFinal);
	}
}

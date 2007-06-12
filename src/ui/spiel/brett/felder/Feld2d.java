package ui.spiel.brett.felder;

import java.awt.Point;
import javax.swing.Icon;
import pd.brett.Feld;
import ui.spiel.brett.FeldMouseAdapter;

/**
 * JLabel, ist die Oberklase aller Felder. Von dieser Klasse leiten alle
 * weiteren Typen von Feldern ab.
 */
public class Feld2d extends javax.swing.JLabel {
	private Point position;
	public final Feld feld;
	private Icon icon;

	public Feld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter, Icon icon) {
		super(icon);
		this.icon = icon;
		this.position = p;
		this.feld = feld;

		setBounds(getPosX(), getPosY(), icon.getIconWidth(), icon.getIconHeight());
		addMouseListener(mouseAdapter);
	}

	public int getPointX() {
		return (int) this.position.getX();
	}

	public int getPointY() {
		return (int) this.position.getY();
	}

	public int getPosX() {
		int pos = (int) position.getX() - (this.icon.getIconWidth() / 2);
		return pos;
	}

	public int getPosY() {
		int pos = (int) position.getY() - (this.icon.getIconHeight() / 2);
		return pos;
	}

	public Feld getFeld() {
		return feld;
	}
}

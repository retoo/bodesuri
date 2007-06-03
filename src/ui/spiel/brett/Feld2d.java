package ui.spiel.brett;

import java.awt.Point;

import javax.swing.ImageIcon;

import pd.brett.Feld;

/**
 * JLabel, ist die Oberklase aller Felder. Von dieser Klasse leiten alle
 * weiteren Typen von Feldern ab.
 */
public class Feld2d extends javax.swing.JLabel {
	private Point position;
	final Feld feld;

	public Feld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter,
	        ImageIcon icon) {
		super(icon);
		this.position = p;
		this.feld = feld;

		setBounds((int) p.getX(), (int) p.getY(), icon.getIconWidth(),
		          icon.getIconHeight());
		addMouseListener(mouseAdapter);
	}

	public int getX() {
		return (int) position.getX();
	}

	public int getY() {
		return (int) position.getY();
	}

	public Feld getFeld() {
		return feld;
	}
}

package ui.spiel.brett;

import java.awt.Point;

import javax.swing.ImageIcon;

import pd.brett.Feld;

public class Feld2d extends javax.swing.JLabel {
	private static final long serialVersionUID = 1L;
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

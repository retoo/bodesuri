package ui.brett;

import java.awt.Point;

import javax.swing.ImageIcon;

import pd.brett.Feld;

public class Feld2d extends javax.swing.JLabel {
	private static final long serialVersionUID = 1L;
	private Point p;
	final Feld feld;

	static final private ImageIcon bildFeld = new ImageIcon(Feld2d.class.getResource("/ui/ressourcen/feld.png"));

	public Feld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(bildFeld);
		this.p = p;
		this.feld = feld;

		setBounds((int)p.getX(), (int)p.getY(), bildFeld.getIconWidth(), bildFeld.getIconHeight());
		addMouseListener(mouseAdapter);
	}
	
	public int getX() {
		return (int) p.getX();
	}
	
	public int getY(){
		return (int) p.getY();
	}
	
	public Feld getFeld() {
		return feld;
	}
}

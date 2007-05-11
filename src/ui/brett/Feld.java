package ui.brett;

import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;

import pd.spieler.Figur;

public class Feld extends javax.swing.JLabel {
	private static final long serialVersionUID = 1L;
	
	static private ImageIcon bildTöggel = new ImageIcon("user.png");
	static private ImageIcon bildFeld = new ImageIcon("basket.png");

	private Figur figur;

	public Feld(MouseAdapter mymouse, int x, int y, String text) {
		super(bildFeld);
		setBounds(x, y, bildFeld.getIconWidth(), bildFeld.getIconHeight());
		setText(text);
		addMouseListener(mymouse);
	}
	
	

	public void setFigur(Figur figur) {
		this.figur = figur;
		if (figur == null) {
			this.setIcon(bildFeld);
		} else {
			this.setIcon(bildTöggel);
		}
	}
	
	public Figur getFigur() {
		return figur;
	}

	public boolean hasFigur() {
		if (figur != null) {
			return true;
		}
		return false;
	}
}

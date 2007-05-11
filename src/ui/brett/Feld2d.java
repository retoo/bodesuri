package ui.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import pd.spieler.Figur;

public class Feld2d extends javax.swing.JLabel {
	private static final long serialVersionUID = 1L;

	static private ImageIcon bildFeld = new ImageIcon("basket.png");

	public Feld2d(int i) {
		super(bildFeld);
		int x = i * 20 + 10;
		int y = 50;
	
		setBounds(x, y, bildFeld.getIconWidth(), bildFeld.getIconHeight());
		addMouseListener(new FeldMouse());
	}

	class FeldMouse extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			Feld2d clicked = (Feld2d) e.getComponent();
		}
	}
}

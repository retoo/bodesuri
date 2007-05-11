package ui.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Feld2d extends javax.swing.JLabel {
	private static final long serialVersionUID = 1L;

	static final private ImageIcon bildFeld = new ImageIcon(Feld2d.class.getResource("/ui/brett/feld.png"));

	public Feld2d(int i) {
		super(bildFeld);
		int x = i * 17 + 10;
		int y = 20;

		setBounds(x, y, bildFeld.getIconWidth(), bildFeld.getIconHeight());
		addMouseListener(new FeldMouse());
	}

	class FeldMouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			// Feld2d clicked = (Feld2d) e.getComponent();
		}
	}
}

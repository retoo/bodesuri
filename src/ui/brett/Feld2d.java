package ui.brett;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Feld2d extends javax.swing.JLabel {
	private static final long serialVersionUID = 1L;
	private Point p;

	static final private ImageIcon bildFeld = new ImageIcon(Feld2d.class.getResource("/ui/brett/feld.png"));

	public Feld2d(Point p) {
		super(bildFeld);
		this.p = p;

		setBounds((int)p.getX(), (int)p.getY(), bildFeld.getIconWidth(), bildFeld.getIconHeight());
		addMouseListener(new FeldMouse());
	}
	
	public int getX(){
		return (int) p.getX();
	}
	
	public int getY(){
		return (int) p.getY();
	}

	class FeldMouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			// Feld2d clicked = (Feld2d) e.getComponent();
		}
	}
}

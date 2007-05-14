package ui.brett;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Figur2d extends JLabel {
	private static final long serialVersionUID = 1L;
	static final private ImageIcon bildFigur = new ImageIcon(Feld2d.class.getResource("/ui/brett/figur.png"));

	public Figur2d(Feld2d feld) {
		super(bildFigur);
		setzeAuf(feld);
	}
	
	public void setzeAuf(Feld2d feld) {
		setBounds(feld.getX(), feld.getY(), bildFigur.getIconWidth(),
		          bildFigur.getIconHeight());
	}
}

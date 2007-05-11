package ui.brett;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Figur extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private ImageIcon bildTöggel = new ImageIcon("user.png");

	public Figur(int x, int y, String text) {
		super(bildTöggel);
		setBounds(x, y, bildTöggel.getIconWidth(), bildTöggel.getIconHeight());
		setText(text);

	}
}

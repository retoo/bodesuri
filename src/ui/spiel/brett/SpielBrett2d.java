package ui.spiel.brett;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SpielBrett2d extends JLabel {
	static final private ImageIcon spielBrett = new ImageIcon(Feld2d.class
			.getResource("/ui/ressourcen/wasser/spielbrett.png"));

	public SpielBrett2d(){
		super(spielBrett);
		setBounds(0, 0, spielBrett.getIconWidth(), spielBrett.getIconHeight());
	}
}

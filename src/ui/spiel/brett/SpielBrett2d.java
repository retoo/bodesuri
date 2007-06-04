package ui.spiel.brett;

import javax.swing.JLabel;
import ui.ressourcen.Icons;

public class SpielBrett2d extends JLabel {
	public SpielBrett2d(){
		super(Icons.SPIELBRETT);
		setBounds(0, 0, Icons.SPIELBRETT.getIconWidth(), Icons.SPIELBRETT.getIconHeight());
	}
}

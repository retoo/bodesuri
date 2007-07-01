package ui.spiel.brett.felder;


import javax.swing.Icon;

import pd.spiel.spieler.Figur;
import ui.geteiltes.BLabel;

/**
 * JLabel, wird zur Darstellung der Spielfiguren verwendet.
 */
public class Figur2d extends BLabel {
	public Figur2d(Figur figur, Icon icon) {
		super(icon, -1, -1);
	}
}

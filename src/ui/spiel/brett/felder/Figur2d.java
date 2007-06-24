package ui.spiel.brett.felder;


import java.awt.Point;

import javax.swing.Icon;

import pd.spieler.Figur;
import ui.geteiltes.BLabel;

/**
 * JLabel, wird zur Darstellung der Spielfiguren verwendet.
 */
public class Figur2d extends BLabel {
	public Figur2d(Figur figur, Icon icon) {
		super(icon, -1, -1);
	}

	/**
	 * Die Spielfigur wird auf das ausgewählte Feld gesetzt.
	 * @param p
	 *
	 */
	public void setzeAuf(Point p) {
		zentriereAuf(p);
	}
}

package ui.spiel.brett.felder;

import geteiltes.BLabel;

import java.awt.Point;

import javax.swing.Icon;

import pd.spieler.Figur;

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

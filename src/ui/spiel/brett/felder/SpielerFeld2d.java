package ui.spiel.brett.felder;

import javax.swing.Icon;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss, bevor er auf
 * die weiteren normalen Feldern weiter ziehe kann
 */
public class SpielerFeld2d extends Feld2d {
	public SpielerFeld2d(Icon icon, Feld2dKonfiguration konfig) {
		super(icon, konfig);
	}
}

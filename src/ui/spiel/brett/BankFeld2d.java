package ui.spiel.brett;

import java.awt.Point;

import pd.brett.Feld;
import ui.ressourcen.Icons;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss,
 * bevor er auf die weiteren normalen Feldern weiter ziehe kann
 */
public class BankFeld2d extends Feld2d {

	public BankFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(p, feld, mouseAdapter, Icons.FELD_NORMAL);
	}
}

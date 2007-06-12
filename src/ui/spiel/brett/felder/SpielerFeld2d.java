package ui.spiel.brett.felder;

import java.awt.Point;

import pd.brett.Feld;
import ui.ressourcen.Icons;
import ui.spiel.brett.FeldMouseAdapter;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss,
 * bevor er auf die weiteren normalen Feldern weiter ziehe kann
 */
public class SpielerFeld2d extends Feld2d {

	public SpielerFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(p, feld, mouseAdapter, Icons.FELD_BANK);
	}
}

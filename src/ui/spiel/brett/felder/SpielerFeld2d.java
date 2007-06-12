package ui.spiel.brett.felder;

import java.awt.Point;

import javax.swing.Icon;

import pd.brett.Feld;
import ui.ressourcen.Icons;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss,
 * bevor er auf die weiteren normalen Feldern weiter ziehe kann
 */
public class SpielerFeld2d extends Feld2d {
	public SpielerFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter, Icon icon) {
		super(p, feld, mouseAdapter, icon);
	}
	
	@Override
	public void setAusgewaehlt(boolean istAusgewaehlt, Feld feld) {
		if (istAusgewaehlt) {
			setIcon(Icons.FELD_AUSWAHL);
		} else {
			setIcon(this.icon);
		}
		updateUI();
	}
}

package ui.spiel.brett.felder;

import java.awt.Point;
import java.util.IdentityHashMap;

import javax.swing.Icon;

import pd.brett.Feld;
import pd.brett.SpielerFeld;
import pd.spieler.SpielerFarbe;
import ui.ressourcen.Icons;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss,
 * bevor er auf die weiteren normalen Feldern weiter ziehe kann
 */
public class SpielerFeld2d extends Feld2d {

	private IdentityHashMap<SpielerFarbe, Icon> farbeMap;

	public SpielerFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter, IdentityHashMap<SpielerFarbe, Icon> farbeMap) {
		super(p, feld, mouseAdapter, farbeMap.get(((SpielerFeld) feld).getSpieler().getFarbe()));
		this.farbeMap = farbeMap;
	}
	
	@Override
	public void setAusgewaehlt(boolean istAusgewaehlt, Feld feld) {
		if (istAusgewaehlt) {
			icon = Icons.FELD_AUSWAHL;
			setIcon(icon);
		} else {
			icon = farbeMap.get(((SpielerFeld)feld).getSpieler().getFarbe());
			setIcon(icon);
		}
		updateUI();
	}
}

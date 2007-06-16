package ui.spiel.brett.felder;

import java.awt.Point;

import javax.swing.Icon;

import applikation.client.pd.Feld;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss, bevor er auf
 * die weiteren normalen Feldern weiter ziehe kann
 */
public class SpielerFeld2d extends Feld2d {
	public SpielerFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter,
			Icon icon, FigurenManager figurenManager) {
		super(p, feld, mouseAdapter, icon, figurenManager);
	}
}

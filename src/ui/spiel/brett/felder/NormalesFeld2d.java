package ui.spiel.brett.felder;

import java.awt.Point;

import pd.spieler.SpielerFarbe;

import ui.erweiterungen.BLabel;
import ui.ressourcen.Icons;
import applikation.client.pd.Feld;

/**
 * JLabel, wird zur Darstellung der normalen Felder verwendet.
 */
public class NormalesFeld2d extends Feld2d {
	public NormalesFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter,
	        BLabel hover, SpielerFarbe farbe, FigurenManager figurenManager) {
		super(p, feld, mouseAdapter, Icons.FELD_NORMAL, hover, farbe, figurenManager);
	}
}

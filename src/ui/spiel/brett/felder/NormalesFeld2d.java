package ui.spiel.brett.felder;

import java.awt.Point;

import ui.ressourcen.Icons;
import applikation.client.pd.Feld;

/**
 * JLabel, wird zur Darstellung der normalen Felder verwendet.
 */
public class NormalesFeld2d extends Feld2d {
	public NormalesFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter, FigurenManager figurenManager) {
		super(p, feld, mouseAdapter, Icons.FELD_NORMAL, figurenManager);
	}
}

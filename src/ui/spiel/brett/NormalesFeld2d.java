package ui.spiel.brett;

import java.awt.Point;

import pd.brett.Feld;
import ui.ressourcen.Icons;

/**
 * JLabel, wird zur Darstellung der normalen Felder verwendet.
 */
public class NormalesFeld2d extends Feld2d {

	public NormalesFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(p, feld, mouseAdapter, Icons.FELD_NORMAL);
	}

}

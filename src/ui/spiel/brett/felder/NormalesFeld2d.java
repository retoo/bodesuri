package ui.spiel.brett.felder;

import java.awt.Point;

import pd.brett.Feld;
import ui.ressourcen.Icons;
import ui.spiel.brett.FeldMouseAdapter;

/**
 * JLabel, wird zur Darstellung der normalen Felder verwendet.
 */
public class NormalesFeld2d extends Feld2d {

	public NormalesFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(p, feld, mouseAdapter, Icons.FELD_NORMAL);
	}

	@Override
	public void setAusgewaehlt(boolean istAusgewaehlt) {
		if (istAusgewaehlt) {
			icon = Icons.FELD_AUSWAHL;
			setIcon(icon);
		} else {
			icon = Icons.FELD_NORMAL;
			setIcon(icon);
		}
		setBounds(getPosX(), getPosY(), icon.getIconWidth(), icon.getIconHeight());
		updateUI();
	}
}

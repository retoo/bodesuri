package ui.spiel.brett;

import java.awt.Point;

import javax.swing.ImageIcon;

import pd.brett.Feld;

/**
 * JLabel, wird zur Darstellung der normalen Felder verwendet.
 */
public class NormalesFeld2d extends Feld2d {
	static final private ImageIcon icon  = new ImageIcon(Feld2d.class.getResource("/ui/ressourcen/feld.png"));

	public NormalesFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(p, feld, mouseAdapter, icon);
	}

}

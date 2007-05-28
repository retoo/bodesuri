package ui.spiel.brett;

import java.awt.Point;

import javax.swing.ImageIcon;

import pd.brett.Feld;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss, 
 * bevor er auf die weiteren normalen Feldern weiter ziehe kann
 */
public class BankFeld2d extends Feld2d {
	private static final long serialVersionUID = 1L;
    
    static final private ImageIcon icon = new ImageIcon(Feld2d.class.getResource("/ui/ressourcen/bankfeld.png"));

    public BankFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
    	super(p, feld, mouseAdapter, icon);
    }
}

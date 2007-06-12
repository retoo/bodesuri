package ui.spiel.brett.felder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pd.brett.Feld;
import ui.GUIController;
import ui.spiel.brett.BrettView;
import ui.spiel.brett.Figur2d;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
public class FeldMouseAdapter extends MouseAdapter {
	private BrettView brettView;
	private GUIController controller;
	private Boolean aktiv;

	public FeldMouseAdapter(BrettView brettView, GUIController controller) {
		this.brettView = brettView;
		this.controller = controller;
		controller.registriereFeldMouseAdapter(this);
		aktiv = false;
	}

	public void mouseEntered(MouseEvent e) {
		controller.zielHover(((Feld2d) e.getComponent()).getFeld());
	}

	/**
	 * Das {@link Feld2d} auf das geklickt wird an den Controller weiterleiten
	 * und falls eine {@link Figur2d} auf dem Feld ist, diese als ausgewählt
	 * markieren.
	 *
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO: Prüfen, ob Figur auch vom eigenen Spieler ist
		Feld feld = ((Feld2d) e.getComponent()).getFeld();
		Figur2d figur2d = brettView.getFigur2d(feld.getFigur());
		if (figur2d != null) {
			controller.feldAuswaehlen(feld);
		}
	}
	
	/**
	 * Hilfsmethode welche eine Figur als ausgewählt oder nicht-ausgewählt darstellt.
	 * 
	 * @param istAusgewaehlt
	 * 				Zur Unterscheidung, ob Figur ausgewählt oder nicht-ausgewählt erscheinen soll
	 * @param feld
	 * 				Angeklicktes Feld
	 */
	public void setzeFigurAusgewaehltStatus(boolean istAusgewaehlt, Feld feld) {
		if (aktiv) {
			// TODO: Prüfen, ob Figur auch vom eigenen Spieler ist
			Figur2d figur2d = brettView.getFigur2d(feld.getFigur());
			Feld2d feld2d = brettView.getFeld2d(feld);  
			if (istAusgewaehlt && figur2d != null) {
				feld2d.setAusgewaehlt(true, feld);
			} else {
				feld2d.setAusgewaehlt(false, feld);
			}
		}
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
    }
}

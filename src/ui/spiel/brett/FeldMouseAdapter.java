package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pd.brett.Feld;
import ui.GUIController;
import ui.spiel.brett.felder.Feld2d;

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
		controller.zielHover(((Feld2d) e.getComponent()).feld);
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
		Feld feld = ((Feld2d) e.getComponent()).feld;
		controller.feldAuswaehlen(feld);
	}
	
	/**
	 * Hilfsmethode welche eine Figur als ausgewählt oder nicht-ausgewählt darstellt.
	 * 
	 * @param istAusgewaehlt
	 * 				Zur Unterscheidung, ob Figur ausgewählt oder nicht-ausgewählt erscheinen soll
	 * @param feld
	 * 				Angeklicktes Feld
	 */
	public void setzeFeldAusgewaehltStatus(boolean istAusgewaehlt, Feld feld) {
		if (aktiv) {
			Feld2d feld2d = brettView.getFeld2d(feld);
			if (istAusgewaehlt) {
				feld2d.setAusgewaehlt(true);
			} else {
				feld2d.setAusgewaehlt(false);
			}
		}
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
    }
}

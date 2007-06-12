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
		controller.feldGewaehlt(feld);
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
			Figur2d figur = brettView.getFigur2d(feld.getFigur());
			if (figur != null) {
				if (istAusgewaehlt) {
					figur.setzeAusgewaehlt();
				} else {
					figur.setzeNichtAusgewaehlt();
				}
			}
		}
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
    }
}

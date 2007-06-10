package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pd.brett.Feld;
import ui.GUIController;
import applikation.client.controller.Controller;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
public class FeldMouseAdapter extends MouseAdapter {
	private BrettView brettView;
	private Controller controller;
	private Boolean aktiv;

	public FeldMouseAdapter(BrettView brettView, GUIController controller) {
		this.brettView = brettView;
		this.controller = controller;
		controller.registriereFeldMouseAdapter(this);
		aktiv = false;
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
		if (aktiv) {
			Feld feld = ((Feld2d) e.getComponent()).feld;
			controller.feldGewaehlt(feld);
			
			// TODO: Das ganze soll auch funktionieren, wenn man nur auf das BrettView klickt
			
			setzeFigurAusgewaehlt(true, feld);
		}
	}
	
	/**
	 * Die Figur, die im {@link Feld2d} steht, auf das erneut geklickt wurde, wird wieder
	 * abgewählt.
	 * 
	 * @param feld
	 * 				Erneut angeklicktes Feld
	 */
	public void feldAbwaehlen(Feld feld) {
		if (aktiv) {
			setzeFigurAusgewaehlt(false, feld);
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
	private void setzeFigurAusgewaehlt(boolean istAusgewaehlt, Feld feld) {
		Figur2d figur = brettView.getFigur2d(feld.getFigur());
		if (figur != null) {
			if (istAusgewaehlt) {
				figur.setzeAusgewaehlt();
			} else {
				figur.setzeNichtAusgewaehlt();
			}
		}
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
    }
}

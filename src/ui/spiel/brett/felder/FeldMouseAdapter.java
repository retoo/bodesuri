package ui.spiel.brett.felder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Feld;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
public class FeldMouseAdapter extends MouseAdapter {
	private Steuerung steuerung;
	private boolean betaetigt;

	public FeldMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void mouseEntered(MouseEvent e) {
		steuerung.hoverStart(((Feld2d) e.getComponent()).getFeld());
	}


	public void mouseExited(MouseEvent e) {
		steuerung.hoverEnde(((Feld2d) e.getComponent()).getFeld());
		if (betaetigt) {
			betaetigt = false;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		betaetigt = true;
	}
	
	/**
	 * Das {@link Feld2d} auf das geklickt wird an den Controller weiterleiten
	 * und falls eine {@link Figur2d} auf dem Feld ist, diese als ausgewählt
	 * markieren.
	 *
	 * TODO: javadoc stimmt nicht mehr! (--> pascal)
	 *
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mouseReleased(MouseEvent e) {
		if (betaetigt) {
			betaetigt = false;
			Feld feld = ((Feld2d) e.getComponent()).getFeld();
			steuerung.feldAuswaehlen(feld);
		}
	}
}

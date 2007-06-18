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

	/**
	 * Ein boolean wird gesetzt, der in <code>mouseReleased()</code> und 
	 * <code>mousePressed()</code> verwendet wird um <code>mouseClicked()</code> 
	 * zu simulieren. Hover-Ende-Effekt wird hier implementiert.
	 *
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mouseExited(MouseEvent e) {
		steuerung.hoverEnde(((Feld2d) e.getComponent()).getFeld());
		betaetigt = false;
	}
	
	/**
	 * Ein boolean wird gesetzt, der in <code>mouseReleased()</code> und 
	 * <code>mouseExited()</code> verwendet wird um <code>mouseClicked()</code> 
	 * zu simulieren.
	 *
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mousePressed(MouseEvent e) {
		betaetigt = true;
	}
	
	/**
	 * Aus dem {@link Feld2d} das {@link Feld} extrahieren und an die 
	 * {@link Steuerung} weiterleiten.
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

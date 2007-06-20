package ui.spiel.brett.felder;

import java.awt.event.MouseEvent;

import ui.erweiterungen.ClickMouseAdapter;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Feld;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
public class FeldMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public FeldMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		steuerung.hoverStart(((Feld2d) e.getComponent()).getFeld());
	}

	/**
	 * Hover-Ende-Effekt wird hier implementiert.
	 * 
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		steuerung.hoverEnde(((Feld2d) e.getComponent()).getFeld());
	}

	/**
	 * Aus dem {@link Feld2d} das {@link Feld} extrahieren und an die
	 * {@link Steuerung} weiterleiten.
	 * 
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void clicked(MouseEvent e) {
		Feld feld = ((Feld2d) e.getComponent()).getFeld();
		steuerung.feldAuswaehlen(feld);
	}
}

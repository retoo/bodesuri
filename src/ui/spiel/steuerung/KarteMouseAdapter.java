package ui.spiel.steuerung;

import java.awt.event.MouseEvent;

import ui.geteiltes.ClickMouseAdapter;

import applikation.client.controller.Steuerung;

/**
 * Erweiterung des ClickMouseAdapter. Bei einem einfachen Klick der Karte wird
 * sie ausgewählt markiert. Bei einem Doppelklick wird die Karte ausgetausch,
 * sofern der richtige Zustand vorhanden ist.
 */
public class KarteMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public KarteMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	/**
	 * Bei einem einfachen Klick wird die Karte ausgewählt markiert.
	 */
	public void clicked(MouseEvent evt) {
		KarteView karteView = (KarteView) evt.getComponent();
		steuerung.karteAuswaehlen(karteView.getKarte());
	}

	/**
	 * Bei einem Doppelklick wird die Karte mit dem Partner ausgetauscht, sofern
	 * sich der Spieler im richtigen Zustand befindet.
	 */
	public void mouseClicked(MouseEvent evt) {
		super.mouseClicked(evt);
		if (evt.getClickCount() == 2) {
			steuerung.kartenTauschBestaetigen();
		}
	}
}

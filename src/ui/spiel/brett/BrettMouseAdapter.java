package ui.spiel.brett;

import java.awt.event.MouseEvent;
import ui.geteiltes.ClickMouseAdapter;
import applikation.client.controller.Steuerung;

/**
 * Erweiterung des ClickMouseAdapter. Beim klicken auf das Spielbrett wird das
 * jeweilige Feld abgewählt, das man davor ausgewählt hatte.
 */
public class BrettMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public BrettMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void clicked(MouseEvent e) {
		steuerung.feldAbwaehlen();
	}
}

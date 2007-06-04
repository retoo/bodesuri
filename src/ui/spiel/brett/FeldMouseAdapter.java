package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import applikation.client.ClientController;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
class FeldMouseAdapter extends MouseAdapter {
	ClientController controller;
	private HashMap<Feld2d, Figur2d> felder = new HashMap<Feld2d, Figur2d>();

	public FeldMouseAdapter(ClientController controller) {
		this.controller = controller;
	}

	/**
	 * Anhand der Instanzvariablen wird entschieden, ob gerade der Anfangspunkt
	 * oder der Endpunkt angeklickt wurde.
	 * 
	 * @param e
	 *            MouseEvent enthält die angeklickte Komponente
	 */
	public void mouseClicked(MouseEvent e) {
		if (controller.isZugAutomatControllerVorhanden()) {
			controller.klickFeld(((Feld2d) e.getComponent()).feld);
			//FIXME: Unterscheidung zwischen 1. und 2. Feld irgendwie noch nicht möglich
			Figur2d figur = getFigur2d(((Feld2d) e.getComponent()));
			if (figur != null) {
				figur.setzeAusgewaehlt();	
			}
		} 
	}

	public void addFigur(Feld2d feld2d, Figur2d figur2d) {
		this.felder.put(feld2d, figur2d);
	}

	public Figur2d getFigur2d(Feld2d feld2d) {
		return this.felder.get(feld2d);
	}
}

package ui.spiel.brett.felder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.GUIController;
import ui.spiel.brett.Figur2d;
import applikation.client.pd.Feld;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
public class FeldMouseAdapter extends MouseAdapter {
	private GUIController controller;

	public FeldMouseAdapter(GUIController controller) {
		this.controller = controller;
	}

	public void mouseEntered(MouseEvent e) {
		controller.zielHover(((Feld2d) e.getComponent()).getFeld());
	}

	/**
	 * Das {@link Feld2d} auf das geklickt wird an den Controller weiterleiten
	 * und falls eine {@link Figur2d} auf dem Feld ist, diese als ausgewählt
	 * markieren.
	 *
	 * TODO: javadoc stimmt nicht mehr! (-reto)
	 *
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mouseClicked(MouseEvent e) {
		Feld feld = ((Feld2d) e.getComponent()).getFeld();
		controller.feldAuswaehlen(feld);
	}
}

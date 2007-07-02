package ui.verbinden;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import applikation.client.controller.Steuerung;

/**
 * Stellt eine Action für den Abbrechen-Button im VerbindenView dar.
 */
public class BeendenAction extends AbstractAction {
	private Steuerung steuerung;

	/**
	 * Erzeugt eine Action für den Abbrechen-Button.
	 *
	 * @param name
	 *            Name der Action.
	 * @param steuerung
	 *            Referenz zum Steuerung.
	 */
	public BeendenAction(String name, Steuerung steuerung) {
		super(name);
		this.steuerung = steuerung;
	}

	public void actionPerformed(ActionEvent e) {
		steuerung.beenden();
	}
}

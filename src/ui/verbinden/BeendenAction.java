package ui.verbinden;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Stellt eine Action für den Abbrechen-Button im VerbindenView dar.
 */
public class BeendenAction extends AbstractAction {
	private VerbindenView verbindenView;

	/**
	 * Erzeugt eine Action für den Abbrechen-Button.
	 * 
	 * @param name
	 *            Name der Action.
	 * @param verbindenView
	 *            Referenz zum VerbindenView.
	 */
	public BeendenAction(String name, VerbindenView verbindenView) {
		super(name);
		this.verbindenView = verbindenView;
	}

	public void actionPerformed(ActionEvent e) {
		verbindenView.beenden();
	}
}

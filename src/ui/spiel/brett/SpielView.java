package ui.spiel.brett;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.spiel.GUIController;
import ui.spiel.karten.KartenAuswahlView;

public class SpielView extends JPanel {
	public SpielView(GUIController controller) {
		// Layout setzen
		setLayout(new BorderLayout());

		// Views
		SpielBrettView spielBrettView = new SpielBrettView(controller);
		KartenAuswahlView kartenAuswahlView = new KartenAuswahlView(controller);

		// Layout zusammenstellen
		add(spielBrettView, BorderLayout.CENTER);
		add(kartenAuswahlView, BorderLayout.EAST);
	}
}

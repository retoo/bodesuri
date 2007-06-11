package ui.spiel.brett;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.GUIController;
import ui.spiel.karten.KartenAuswahlView;

public class SpielView extends JPanel {
	public SpielView(GUIController controller) {
		// Layout setzen
		setLayout(new BorderLayout());

		// Views
		SpielBrettView spielBrettView = new SpielBrettView(controller);
		KartenAuswahlView kartenAuswahlView = new KartenAuswahlView(controller);

		// Filztextur wird hinzugefügt
		// TODO: hm... Irgendwie überdeckt es das ganze Spiel ( am richtigen Ort
		// einfügen )
		//add(new FilzView());

		// Layout zusammenstellen
		add(spielBrettView, BorderLayout.CENTER);
		add(kartenAuswahlView, BorderLayout.EAST);
	}
}

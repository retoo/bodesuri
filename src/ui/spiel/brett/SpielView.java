package ui.spiel.brett;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ui.spiel.karten.KartenInfoView;

import applikation.client.Controller;

public class SpielView extends JPanel {
	public SpielView(Controller controller) {
		// Layout setzen
		setLayout(new BorderLayout());

		// Views
		SpielBrettView spielBrettView = new SpielBrettView(controller);
		KartenInfoView kartenInfoView = new KartenInfoView(controller);

		// Layout zusammenstellen
		add(spielBrettView, BorderLayout.CENTER);
		add(kartenInfoView, BorderLayout.EAST);
	}
}

package ui.spiel.brett;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;

import javax.swing.JPanel;

import pd.Spiel;
import pd.spieler.Spieler;

import ui.GUIController;

public class SpielBrettView extends JPanel {
	public SpielBrettView(GUIController controller, Spiel spiel, Map<Spieler, applikation.client.pd.Spieler> spielers) {
		setOpaque(false);
		// Layout
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		// Views
		JPanel brettView = new BrettView(controller, spiel, spielers);
		
		// Spezielles Verfahren, um ein JPanel zu zentrieren
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;		
		gbl.setConstraints(brettView, gbc);
		add(brettView);
	}
}

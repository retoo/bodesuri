package ui.spiel.brett;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class SpielBrettView extends JPanel {
	public SpielBrettView(Steuerung steuerung, Spiel spiel) {
		setOpaque(false);
		// Layout
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		// Views
		JPanel brettView = new BrettView(steuerung, spiel);

		// Spezielles Verfahren, um ein JPanel zu zentrieren
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbl.setConstraints(brettView, gbc);
		add(Box.createRigidArea(new Dimension(10,620)));
		add(brettView);
		add(Box.createRigidArea(new Dimension(10,620)));
	}
}

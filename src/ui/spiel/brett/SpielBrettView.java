package ui.spiel.brett;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import applikation.client.Controller;

public class SpielBrettView extends JPanel {
	public SpielBrettView(Controller controller) {
		// Layout
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		// Views
		JPanel brettView = new BrettView(controller);
		
		// Spezielles Verfahren, um ein JPanel zu zentrieren
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;		
		gbl.setConstraints(brettView, gbc);
		add(brettView);
	}
}

package ui.spiel.brett;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import applikation.client.Controller;



public class SpielBrettView extends JPanel {
	public SpielBrettView(Controller controller) {
		GridBagLayout gbl = new GridBagLayout();

		TitledBorder titel = new TitledBorder("Spiel");
		setBorder(titel);
		setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		JPanel brettView = new BrettView(controller);
		gbl.setConstraints(brettView, gbc);
		add(brettView);
	}
}

package ui.spiel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ui.spiel.brett.BrettView;
import applikation.client.BodesuriClient;
import applikation.client.ClientController;

public class SpielBrettView extends JPanel {
	private static final long serialVersionUID = 1L;

	public SpielBrettView(ClientController controller) {
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

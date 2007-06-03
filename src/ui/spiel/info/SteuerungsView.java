package ui.spiel.info;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import applikation.client.ClientController;

public class SteuerungsView extends JPanel {
	ClientController controller;

	public SteuerungsView(ClientController controller) {
		this.controller = controller;

		TitledBorder titel = new TitledBorder("Steuerung");
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel spielZustand = new JLabel("Zustand");
		JLabel spielAktion = new JLabel(" Karte auswählen.");
		JButton zugAusfuehren = new JButton("Ausführen");
		JButton aussetzen = new JButton("Aussetzen");

		add(spielZustand);
		add(spielAktion);
		add(zugAusfuehren);
		add(aussetzen);
	}

}

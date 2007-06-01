package ui.spiel.info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import applikation.client.ClientController;
import applikation.client.zugautomat.zustaende.KarteWaehlen;

public class SteuerungsView extends JPanel {

	private static final long serialVersionUID = 1L;

	ClientController controller;
	
	public SteuerungsView(ClientController controller) {
		this.controller = controller;
		
		TitledBorder titel = new TitledBorder("Steuerung");
		// titel.setTitleFont(titel.getTitleFont().deriveFont(Font.BOLD));
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel spielZustand = new JLabel("Zustand");
		spielZustand.setFont(new java.awt.Font("Tahoma", 1, 11));
		JLabel spielAktion = new JLabel(" Karte auswählen.");
		
		JButton zugAusfuehren = new JButton("Ausführen");

		// TODO Noch besser platzieren & schöner machen (kein Automat)
		JButton aussetzen = new JButton("Aussetzen");
		aussetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SteuerungsView.this.controller.zugAutomat != null
				    && SteuerungsView.this.controller.zugAutomat.isZustand(KarteWaehlen.class)) {
					// TODO: Fehler beheben SteuerungsView.this.controller.queue.enqueue(new AufgegebenEvent());
				} else {
					System.out.println("Wir sind im falschen Zustand: "
					         + SteuerungsView.this.controller.zugAutomat.getAktuellerZustand());
				}
			}

		});
		
		add(spielZustand);
		add(spielAktion);
		add(zugAusfuehren);
		add(aussetzen);
	}

}

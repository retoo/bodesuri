package ui.spiel.chat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import applikation.client.BodesuriClient;
import applikation.client.zugAutomatAlt.zustaende.KarteWaehlen;
import applikation.events.AufgegebenEvent;

/**
 * Implementierung des Chat-Client, der zur Text-Kommunikation zwischen den
 * einzelnen Clients dient.
 */
public class ChatView extends JPanel {

	private static final long serialVersionUID = 1L;
	private BodesuriClient automat;

	public ChatView(BodesuriClient automat) {
		this.automat = automat;

		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 0, 150));

		this.add(new JLabel("Hier käme der Chat hin... \n(Prio 3)"));

		// TODO Noch besser platzieren & schöner machen (kein Automat)
		JButton aussetzen = new JButton("Aussetzen");
		aussetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ChatView.this.automat.zugAutomat != null
				    && ChatView.this.automat.zugAutomat.isZustand(KarteWaehlen.class)) {
					ChatView.this.automat.queue.enqueue(new AufgegebenEvent());
				} else {
					System.out.println("Wir sind im falschen Zustand: "
					         + ChatView.this.automat.zugAutomat.getAktuellerZustand());
				}
			}

		});
		add(aussetzen);
	}
}

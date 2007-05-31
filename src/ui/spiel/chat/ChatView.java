package ui.spiel.chat;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import applikation.client.ClientController;

/**
 * Implementierung des Chat-Client, der zur Text-Kommunikation zwischen den
 * einzelnen Clients dient.
 */
public class ChatView extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private ClientController controller;

	public ChatView(ClientController controller) {
		this.controller = controller;

		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 0, 150));

		this.add(new JLabel("Hier käme der Chat hin... \n(Prio 3)"));

		// TODO: In ClientController platzieren. Actionlistener soll von Controller verwaltet werden
		/*JButton aussetzen = new JButton("Aussetzen");
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
		add(aussetzen);*/
	}
}

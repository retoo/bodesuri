package ui.lobby;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pd.Spiel;
import pd.spieler.Spieler;
import ui.HauptView;
import applikation.synchronisation.nachrichten.ChatNachricht;
import applikation.synchronisation.nachrichten.NeueVerbindung;
import applikation.synchronisation.nachrichten.SpielBeitreten;
import applikation.synchronisation.nachrichten.SpielStartNachricht;
import applikation.synchronisation.nachrichten.SpielVollNachricht;
import applikation.synchronisation.nachrichten.VerbindungGeschlossen;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;

public class LobbyView extends HauptView {

	private static final long serialVersionUID = 1L;
	private JTextField hostname;
	private JTextField port;
	private JTextField spielerName;

	public LobbyView() {
		setLayout(new GridLayout(4, 2));
		Dimension groesse = new Dimension(600, 600);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
		
		add(new JLabel("Server:"));
		hostname = new JTextField("localhost");
		add(hostname);
		add(new JLabel("Port:"));
		port = new JTextField("7788");
		add(port);
		add(new JLabel("Spieler:"));
		spielerName = new JTextField("Spieler");
		add(spielerName);

		JButton ok = new JButton("OK");
		ok.addMouseListener(new MouseAdapter() {
			// TODO MouseAdapter kann unsere Exceptions nicht werfen...
			public void mouseClicked(MouseEvent e) {
				try {
					verbinden();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (VerbindungWegException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(ok);
	}

	private void verbinden() throws UnknownHostException, IOException,
	                        VerbindungWegException {
		EndPunkt server = null;

		try {

			server = new EndPunkt(hostname.getText(), new Integer(port.getText()));

			server.sende(new SpielBeitreten(spielerName.getText()));

			SpielStartNachricht startNachricht;
			while (true) {
				Brief b = server.briefkasten.getBrief();
				Nachricht nachricht = b.nachricht;

				if (nachricht instanceof SpielStartNachricht) {
					startNachricht = (SpielStartNachricht) b.nachricht;
					break;
				} else if (nachricht instanceof SpielVollNachricht) {
					System.out.println("Leider ist das Spiel bereits voll.");
					System.exit(1);
				} else if (nachricht instanceof ChatNachricht) {
					System.out.println("> " + nachricht);
				} else if (nachricht instanceof VerbindungGeschlossen) {
					System.out
					          .println("Die Verbindung wurde durch den Server unerwartet geschlossen!");
					System.exit(99);
				} else if (nachricht instanceof NeueVerbindung) {
					// Ignorieren
				} else {
					/* TODO: Platzhalter */
					System.out.println("Unbekannte Nachricht: " + nachricht);
				}
			}

			System.out.println();
			System.out.println("Das Spiel kann beginnen. Es spielen mit:");

			for (int i = 0; i < startNachricht.spieler.length; ++i) {
				System.out.println((i + 1) + ") " + startNachricht.spieler[i]);
			}

			System.out.println();

			Spiel spiel = new Spiel();
			Spieler spielerIch = null;

			for (int i = 0; i < startNachricht.spieler.length; i++) {
				String name = startNachricht.spieler[i];

				spiel.fuegeHinzu(name);
				if (name.equals(spielerName)) {
					spielerIch = spiel.getSpieler().get(i);
				}
			}

			if (spielerIch == null) {
				/* FIXME besser machen */
				throw new RuntimeException("Ups, ich bin ja gar nicht im Spiel");
			}

			// run(spiel, spielerIch, server);

		} catch (ConnectException e) {
			System.out
			          .println("Verbindung zum Server konnte nicht aufgebaut werden.");
		} catch (VerbindungWegException v) {
			System.out
			          .println("Die Verbindung zum Server wurde unerwartetet abgebrochen!");
			v.printStackTrace();
		} finally {
			/* So oder so wenn wir hier fertig sind machen wir den Socket zu. */
			if (server != null) {
				server.ausschalten(); /*
										 * FIXME: etwaige Exceptions in diesem
										 * Code können wir in der finalen
										 * Version ignorieren
										 */
			}
		}
	}

}

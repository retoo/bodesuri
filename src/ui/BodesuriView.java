package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pd.Spiel;
import pd.spieler.Spieler;
import ui.brett.BrettView;
import ui.chat.ChatView;
import ui.info.DeckView;
import ui.info.SpielerView;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.VerbindungWegException;
import dienste.netzwerk.nachrichten.ChatNachricht;
import dienste.netzwerk.nachrichten.Nachricht;
import dienste.netzwerk.nachrichten.NeueVerbindung;
import dienste.netzwerk.nachrichten.SpielBeitreten;
import dienste.netzwerk.nachrichten.SpielStartNachricht;
import dienste.netzwerk.nachrichten.SpielVollNachricht;
import dienste.netzwerk.nachrichten.VerbindungGeschlossen;

/**
 * Das GUI des Spieles...
 * 
 */
public class BodesuriView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JMenuBar menuleiste;

	private JMenu spielMenu;

	private JMenuItem spielBeitreten;

	private JMenuItem spielVerlassen;

	private static BrettView brett;

	public BodesuriView() {
		setTitle("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

		// Menu
		menuleiste = new JMenuBar();

		spielMenu = new JMenu("Spiel");
		menuleiste.add(spielMenu);

		spielBeitreten = new JMenuItem("Spiel beitreten");
		spielMenu.add(spielBeitreten);

		spielVerlassen = new JMenuItem("Spiel verlassen");
		spielMenu.add(spielVerlassen);

		setJMenuBar(menuleiste);

		// Panels
		// Box scheint das beste Layout zu sein.
		// - Border geht nicht wegen dem Resize.
		// - GridBag ist zu kompliziert, weil wir unabhängige Spalten haben.
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		Box links = Box.createVerticalBox();
		links.add(brett);
		links.add(new ChatView());

		Box rechts = Box.createVerticalBox();
		rechts.add(new SpielerView());
		rechts.add(new DeckView());
		rechts.add(Box.createVerticalGlue());

		add(links);
		add(rechts);
		add(Box.createHorizontalGlue());
		pack();
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException, VerbindungWegException {
		// Speziell für den Mac
		if (System.getProperty("mrj.version") != null) {
			// Menubar oben
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}

		voruebergehend(args);

		new BodesuriView().setVisible(true);
	}

	private static void voruebergehend(String[] args)
			throws UnknownHostException, IOException, VerbindungWegException {

		String hostname = "localhost";
		String spielerName = "TEST";
		int port = 7788;
		if (args.length >= 1) {
			hostname = args[0];
			if (args.length >= 2) {
				port = Integer.parseInt(args[1]);
				if (args.length >= 3) {
					spielerName = args[2];
				}
			}
		}

		EndPunkt server = null;

		try {

			server = new EndPunkt(hostname, port);

			server.sende(new SpielBeitreten(spielerName));

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

			brett = new BrettView(spiel, spielerIch, server);
			// spielBrett.run();

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

package ui.verbinden;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import applikation.client.events.VerbindenEvent;

import dienste.statemachine.EventQueue;


public class VerbindenView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField hostname;
	private JTextField port;
	private JTextField spielerName;
	private EventQueue queue;

	public VerbindenView(EventQueue queue) {
		this.queue = queue;
		
		setTitle("Bodesuri - Verbinden");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
		
		setLayout(new GridLayout(4, 2));
		Dimension groesse = new Dimension(300, 150);
		setPreferredSize(groesse);
		
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
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
	            // TODO Auto-generated method stub
				String host = hostname.getText();
				String spieler = spielerName.getText();
				Integer port_raw = Integer.valueOf(port.getText());
				
				VerbindenEvent e = new VerbindenEvent(host, port_raw, spieler);
				
				VerbindenView.this.queue.enqueue(e);
            }
		});
		add(ok);
		
		pack();
	}

//	private void verbinden() throws UnknownHostException, IOException,
//	                        VerbindungWegException {
//		EndPunkt server = null;
//
//		try {
//
//			server = new EndPunkt(hostname.getText(), new Integer(port.getText()));
//
//			server.sende(new SpielBeitreten(spielerName.getText()));
//
//			SpielStartNachricht startNachricht;
//			while (true) {
//				Brief b = server.briefkasten.getBrief();
//				Nachricht nachricht = b.nachricht;
//
//				if (nachricht instanceof SpielStartNachricht) {
//					startNachricht = (SpielStartNachricht) b.nachricht;
//					break;
//				} else if (nachricht instanceof SpielVollNachricht) {
//					System.out.println("Leider ist das Spiel bereits voll.");
//					System.exit(1);
//				} else if (nachricht instanceof ChatNachricht) {
//					System.out.println("> " + nachricht);
//				} else if (nachricht instanceof VerbindungGeschlossen) {
//					System.out.println("Die Verbindung wurde durch den Server unerwartet geschlossen!");
//					System.exit(99);
//				} else if (nachricht instanceof NeueVerbindung) {
//					// Ignorieren
//				} else {
//					System.out.println("Unbekannte Nachricht: " + nachricht);
//				}
//			}
//
//			System.out.println();
//			System.out.println("Das Spiel kann beginnen. Es spielen mit:");
//
//			for (int i = 0; i < startNachricht.spieler.length; ++i) {
//				System.out.println((i + 1) + ") " + startNachricht.spieler[i]);
//			}
//
//			System.out.println();
//
//			Spiel spiel = new Spiel();
//			Spieler spielerIch = null;
//
//			for (int i = 0; i < startNachricht.spieler.length; i++) {
//				String name = startNachricht.spieler[i];
//
//				spiel.fuegeHinzu(name);
//				if (name.equals(spielerName)) {
//					spielerIch = spiel.getSpieler().get(i);
//				}
//			}
//
//			if (spielerIch == null) {
//				/* FIXME besser machen */
//				throw new RuntimeException("Ups, ich bin ja gar nicht im Spiel");
//			}
//
//			// run(spiel, spielerIch, server);
//
//		} catch (ConnectException e) {
//			System.out
//			          .println("Verbindung zum Server konnte nicht aufgebaut werden.");
//		} catch (VerbindungWegException v) {
//			System.out
//			          .println("Die Verbindung zum Server wurde unerwartetet abgebrochen!");
//			v.printStackTrace();
//		} finally {
//			/* So oder so wenn wir hier fertig sind machen wir den Socket zu. */
//			if (server != null) {
//				server.ausschalten(); /*
//										 * FIXME: etwaige Exceptions in diesem
//										 * Code können wir in der finalen
//										 * Version ignorieren
//										 */
//			}
//		}
//	}

}

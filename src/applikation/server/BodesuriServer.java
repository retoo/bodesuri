package applikation.server;

import java.util.Vector;

import pd.Spiel;
import applikation.client.events.NetzwerkEvent;
import applikation.server.nachrichten.BeitrittsBestaetigung;
import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.SpielBeitreten;
import applikation.server.nachrichten.SpielStartNachricht;
import applikation.server.nachrichten.VerbindungGeschlossen;
import applikation.server.nachrichten.ZugAufforderung;
import applikation.server.nachrichten.ZugInformation;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.Brief;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;
import dienste.netzwerk.server.Server;

/**
 * Der Server. Wird vom Benutzer gestartet.
 */
public class BodesuriServer extends Automat {
	protected static final int MAXSPIELER = 4;
	Vector<Spieler> spielers = new Vector<Spieler>();
	protected Server server;
	protected EventQueue queue;;

	private BodesuriServer() {
		this.queue = new EventQueue();
		
		registriere(new ServerStart());
		registriere(new EmpfangeSpieler());
		
		setStart(ServerStart.class);
		setEventQuelle(queue);
	}

	private void rundol() throws VerbindungWegException {
		try {
			System.out.println("Server gestartet");
			starteSpiel();
			broadcast("Spiel vollständig.");
			spieleSpiel();
			broadcast("Spiel abgeschlossen.. sollte zurzeit noch nicht passieren");
		} catch (Exception e) {
			/* FIXME: gefällt mir nicht! */
			System.out
			          .println("Unerwarteter schwerer Fehler! Server wird gestoppt!");
			e.printStackTrace();
			System.exit(99);
		}
	}

	private void starteSpiel() throws VerbindungWegException {
		/* potentielle klasse */

		while (true) {
			/*
			 * FIXME: foldgender hässlciher code wird bald im statemachine
			 * verschwinden
			 */
			Brief brief = ((NetzwerkEvent) queue.dequeue()).brief;
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof SpielBeitreten) {
				SpielBeitreten beitrittNachricht = (SpielBeitreten) nachricht;
				Spieler spieler = new Spieler(brief.absender,
				                              beitrittNachricht.spielerName);
				spielers.add(spieler);

				String[] spielers_str = new String[MAXSPIELER];

				for (int i = 0; i < spielers.size(); i++)
					spielers_str[i] = spielers.get(i).spielerName;

				spieler.endpunkt.sende(new BeitrittsBestaetigung(spielers_str));

				String msg = "Neuer Spieler " + spieler.spielerName + ". Noch "
				             + (MAXSPIELER - spielers.size())
				             + " Spieler nötig.";
				broadcast(msg);

				System.out.println("Neuer Spieler: " + spieler);

				if (spielers.size() == MAXSPIELER) {
					return; /* Spiel bereit */
				}
			} else {
				/* Platzhalter für später */
				throw new RuntimeException(
				                           "Unbkenanter Nachrichtentyp in Brief"
				                                   + brief);
			}
		}
	}

	private void spieleSpiel() throws VerbindungWegException {
		Spiel spiel = new Spiel();

		for (Spieler spieler : spielers) {
			spiel.fuegeHinzu(spieler.spielerName);
		}

		kuendigeSpielstartAn();

		/* Versende den ersten Token FIXME: das muss noch schöner :) */
		Klicks klicks = new Klicks(MAXSPIELER);

		Spieler aktuellerSpieler = spielers.get(klicks.klick());

		broadcast(aktuellerSpieler.spielerName + " fängt an.");
		aktuellerSpieler.endpunkt.sende(new ZugAufforderung());
		// FIXME: Timer timer = new ZugAufforderungsTimer(serverBriefkasten);

		System.out.println("Aktueller Spieler: " + aktuellerSpieler);

		while (true) {
			/*
			 * FIXME: foldgender hässlciher code wird bald im statemachine
			 * verschwinden
			 */
			Brief brief = ((NetzwerkEvent) queue.dequeue()).brief;
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof ZugInformation) {
				ZugInformation zugInfo = (ZugInformation) nachricht;

				if (brief.absender != aktuellerSpieler.endpunkt) {
					broadcast("HAH.. huere michi, de " + brief.absender
					          + " wott voll bschisse");
					throw new RuntimeException("beschiss von " + brief + " an "
					                           + aktuellerSpieler);
				}

				broadcast(zugInfo);

				System.out.println("Ausgeführter Zug: "
				                   + zugInfo.zug.getKarte() + " wurde von "
				                   + zugInfo.zug.getSpieler() + " gespielt");

				aktuellerSpieler = spielers.get(klicks.klick());
				broadcast("Nächster Spieler ist " + aktuellerSpieler.spielerName + ".");

				aktuellerSpieler.endpunkt.sende(new ZugAufforderung());

				System.out.println("Nächster Spieler: " + aktuellerSpieler);

			} else if (nachricht instanceof SpielBeitreten) {
				String msg = "Da versucht ein weiterer zu verbinden, aber das Boot ist voll: "
				             + brief;
				System.out.println(msg);
				broadcast(msg);
			} else if (nachricht instanceof VerbindungGeschlossen) {
				System.out.println("Die Verbindung wurde durch den EndPunkt "
				                   + brief.absender
				                   + "  unerwartet geschlossen!");
				System.exit(99);
			} else {
				System.out.println("Unbekannte Nachricht im Brief " + brief);
			}
		}

	}

	private void kuendigeSpielstartAn() throws VerbindungWegException {
		String[] spielers_str = new String[MAXSPIELER];

		for (int i = 0; i < MAXSPIELER; i++)
			spielers_str[i] = spielers.get(i).spielerName;

		SpielStartNachricht ssn = new SpielStartNachricht(spielers_str);
		broadcast(ssn);
	}

	/* potentiele neue klasse */
	private void broadcast(Nachricht nachricht) throws VerbindungWegException {
		for (Spieler spieler : spielers) {
			try {
				spieler.endpunkt.sende(nachricht);
			} catch (VerbindungWegException e) {
				System.out
				          .println("uhuh.. scheint als ob folgender Spieler nicht mehr erreichbar wäre: "
				                   + spieler);
				throw e;
			}
		}
	}

	void broadcast(String msg) throws VerbindungWegException {
		broadcast(new ChatNachricht(msg));
	}

	/**
	 * Startet den Server.
	 * 
	 * @param args
	 *            Wird nicht genutzt.
	 */
	public static void main(String[] args) {
		BodesuriServer server = new BodesuriServer();
		
		server.run();
	}
}

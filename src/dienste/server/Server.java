package dienste.server;

import java.io.IOException;
import java.util.Vector;

import pd.Spiel;
import dienste.netzwerk.Brief;
import dienste.netzwerk.Briefkasten;
import dienste.netzwerk.Daemon;
import dienste.netzwerk.VerbindungWegException;
import dienste.netzwerk.nachrichten.ChatNachricht;
import dienste.netzwerk.nachrichten.Nachricht;
import dienste.netzwerk.nachrichten.NeueVerbindung;
import dienste.netzwerk.nachrichten.SpielBeitreten;
import dienste.netzwerk.nachrichten.SpielStartNachricht;
import dienste.netzwerk.nachrichten.ZugAufforderung;
import dienste.netzwerk.nachrichten.ZugInformation;


public class Server {
	private static final int PORT = 7788;
	private static final int MAXSPIELER = 1;
	private Briefkasten serverBriefkasten;
	private Vector<Spieler> spielers = new Vector<Spieler>();;

	private Server() throws IOException {
		serverBriefkasten = new Briefkasten();

		Daemon a = new Daemon(PORT, serverBriefkasten);

		Thread t = new Thread(a);
		t.start();

	}

	private void run() throws VerbindungWegException {
		starteSpiel();
		broadcast("Spiel vollständig.");
		spieleSpiel();
		broadcast("Spiel abgeschlossen.. sollte zurzeit noch nicht passieren");

	}


	private void starteSpiel() throws VerbindungWegException {
		/* potentielle klasse */


		while (true) {
			Brief brief = serverBriefkasten.getBrief();
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof SpielBeitreten) {
				SpielBeitreten beitrittNachricht = (SpielBeitreten) nachricht;
				Spieler spieler = new Spieler(brief.absender,
				                              beitrittNachricht.spielerName);
				spielers.add(spieler);

				String msg = "Neuer Spieler " + spieler.name + ". Noch "
				             + (MAXSPIELER - spielers.size())
				             + " Spieler nötig.";
				broadcast(msg);

				if (spielers.size() == MAXSPIELER) {
					return; /* Spiel bereit */
				}	
			} else if (nachricht instanceof NeueVerbindung) {
				System.out.println("Neue Verbindung von " + brief);
			} else {
				/* Platzhalter für später */
				throw new RuntimeException("Unbkenanter Nachrichtentyp " + brief);
			}
		}
	}

	private void spieleSpiel() throws VerbindungWegException {
		Spiel spiel = new Spiel();
		
		
		for (Spieler spieler : spielers) {
			spiel.fuegeHinzu(spieler.name);
		}
	
		kuendigeSpielstartAn();
		
		/* Versende den ersten Token  FIXME: das muss noch schöner :) */
		Klicks klicks = new Klicks(MAXSPIELER);
		
		Spieler aktuellerSpieler = spielers.get(klicks.klick());
		
		broadcast(aktuellerSpieler.name + " fängt an.");
		aktuellerSpieler.endpunkt.sende(new ZugAufforderung());
		
		while (true) {
			Brief brief = serverBriefkasten.getBrief();
			Nachricht nachricht = brief.nachricht;
			if (nachricht instanceof ZugInformation ) {
				ZugInformation zugInfo = (ZugInformation) nachricht;
				
				
				if (brief.absender != aktuellerSpieler.endpunkt) {
					broadcast("HAH.. huere michi, de " + brief.absender + " wott voll bschisse" );
					throw new RuntimeException("beschiss von " + brief + " an " + aktuellerSpieler);
				}
				
				broadcast(zugInfo);
				
				aktuellerSpieler = spielers.get(klicks.klick());
				broadcast("Nächster Spieler ist " + aktuellerSpieler.name + ".");
				
				aktuellerSpieler.endpunkt.sende(new ZugAufforderung());
				
			} else if (nachricht instanceof SpielBeitreten) {
				String msg = "Da versucht ein weiterer zu verbinden, aber das Boot ist voll: " + brief;
				System.out.println(msg);
				broadcast(msg);
			}
		}		 

	}

	private void kuendigeSpielstartAn() throws VerbindungWegException {
	    String[] spielers_str = new String[MAXSPIELER];
		
		for (int i = 0; i < MAXSPIELER; i++) 
			spielers_str[i] = spielers.get(i).name;
 		
		SpielStartNachricht ssn = new SpielStartNachricht(spielers_str);
		broadcast(ssn);
    }


	/* potentiele neue klasse */
	private void broadcast(Nachricht nachricht) throws VerbindungWegException {
		for (Spieler spieler : spielers) {
			spieler.endpunkt.sende(nachricht);
		}
	}
	
	
	private void broadcast(String msg) throws VerbindungWegException {
		broadcast(new ChatNachricht(msg));
    }

	public static void main(String[] args) throws IOException, VerbindungWegException {
		Server srv = new Server();

		srv.run();

		System.out.println("Reached end of main");
	}

}

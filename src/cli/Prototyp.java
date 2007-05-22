package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import pd.Spiel;
import pd.brett.BankFeld;
import pd.brett.Brett;
import pd.brett.Feld;
import pd.brett.NormalesFeld;
import pd.karten.Karte;
import pd.regelsystem.RegelVerstoss;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;
import applikation.client.events.NetzwerkEvent;
import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.NeueVerbindung;
import applikation.server.nachrichten.SpielBeitreten;
import applikation.server.nachrichten.SpielStartNachricht;
import applikation.server.nachrichten.SpielVollNachricht;
import applikation.server.nachrichten.VerbindungGeschlossen;
import applikation.server.nachrichten.ZugAufforderung;
import applikation.server.nachrichten.ZugInformation;
import dienste.netzwerk.Brief;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;
import dienste.statemachine.EventQueue;

public class Prototyp {
	private Spiel spiel;
	private Brett brett;
	
	private BankFeld startFeld;
	private EndPunkt server;
	private Spieler lokalerSpieler;
	private EventQueue queue;

	public Prototyp(Spiel spiel, Spieler spielerIch, EndPunkt server, EventQueue queue) {
		this.server = server;
		this.spiel = spiel;
		this.lokalerSpieler = spielerIch;
		this.brett = spiel.getBrett();
		this.startFeld = brett.getBankFeldVon(spiel.getSpieler().get(0));
		this.queue = queue;
		setzeFiguren();
	}
	
	private void setzeFiguren() {
		for (Spieler spieler : spiel.getSpieler()) {
			Feld feld = brett.getBankFeldVon(spieler);
			feld.setFigur(spieler.getFiguren().get(0));
		}
	}

	public void zeichneBrett() {
		System.out.println("0000000000111111111122222222223333333333444444444455555555556666");
		System.out.println("0123456789012345678901234567890123456789012345678901234567890123");
		Feld feld = startFeld;
		while (feld != startFeld.getVorheriges()) {
			zeichneFeld(feld);
			feld = feld.getNaechstes();
		}
		System.out.println();
	}

	public void zeichneFeld(Feld feld) {
		if (feld.istBesetzt()) {
			Spieler spieler = feld.getFigur().getSpieler();
			System.out.print(spiel.getSpieler().indexOf(spieler) + 1);
		} else if (feld instanceof BankFeld) {
			System.out.print("X");
		} else if (feld instanceof NormalesFeld) {
			System.out.print("_");
		}

	}

	public void begruessungAusgaben() {
		System.out.println("Willkommen im Spiel Bodesuri (Prototyp).");
		System.out.println("(Legende: X = Bänkli, _ = normales Feld, " +
		                   "Zahl = Spieler)");
	}

	public Spieler auswahlSpieler() {
		System.out.println("Wähle einen Spieler aus [0-3]:");
		Spieler spieler = spiel.getSpieler().get(liesZahlEin());
		System.out.println(spieler + " wurde ausgewählt.");
		
		return spieler;
	}

	public Karte auswahlKarte(List<Karte> karten) {
		while (true) {
			System.out.println("Du hast folgende Karten zur Auswahl:");
			
			for (int i = 0; i < karten.size(); i++) {
				System.out.println("" + (i+1) + ") " + karten.get(i));
			}
			
			System.out.print("Karte (1-" + karten.size() + "): ");
			int eingabe = liesZahlEin();
			
			if (1 <= eingabe && eingabe <= karten.size()) {
				return karten.get(eingabe - 1);
			}
		}
	}

	public Feld auswahlStartfeld() {
		System.out.print("Startfeld (0-63): ");
		return startFeld.getNtesFeld(liesZahlEin());
	}

	public Feld auswahlZielfeld() {
		System.out.print("Zielfeld  (0-63): ");
		return startFeld.getNtesFeld(liesZahlEin());
	}

	private int liesZahlEin() {
		while (true) {
			try {
				return Integer.parseInt(liesStringEin());
			} catch (NumberFormatException e) {
				/* TODO: prüfen ob das so okay ist */
				System.out.print("Ungültige Zahl, bitte erneut versuchen: ");
			}
		}
	}

	private static String liesStringEin() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			return in.readLine();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
			System.exit(99);
		}
		
		return null; /* wird nie hier hin kommen */
	}
	
	private ZugEingabe erfasseZug(List<Karte> karten) {
	    Karte karte = auswahlKarte(karten);
	    Feld start = auswahlStartfeld();
	    Feld ziel  = auswahlZielfeld();
	    
	    Bewegung bewegung = new Bewegung(start, ziel);
	    ZugEingabe zugEingabe = new ZugEingabe(lokalerSpieler, karte, bewegung);
	    return zugEingabe;
    }


	private void run() throws VerbindungWegException {		
		begruessungAusgaben();
		
		System.out.println();
		zeichneBrett();
		System.out.println();
		
		while (true) {
			// zeichneBrett();
			
			/* FIXME: foldgender hässlciher code wird bald im statemachine verschwinden */
			Brief brief = ((NetzwerkEvent) queue.dequeue()).brief;
			Nachricht nachricht = brief.nachricht;	
			
			if (nachricht instanceof ZugAufforderung) {
				System.out.println();
				System.out.println(lokalerSpieler.getName() + ", du bist am Zug!");
				System.out.println();
				
				List<Karte> karten = spiel.getKartenGeber().getKarten(5);
				
				while (true) {
					ZugEingabe zugEingabe = erfasseZug(karten);
					
					try {
						zugEingabe.validiere();
						server.sende(new ZugInformation(zugEingabe));
						break;
					} catch (RegelVerstoss rv) {
						System.out.println();
						System.out.println(rv);
						System.out.println();
					}
				}
				
			} else if (nachricht instanceof ZugInformation) {
				ZugInformation zn = (ZugInformation) nachricht;
				
				
				ZugEingabe ze = zn.zug;
				
				try {
					Zug zug = ze.validiere();
					zug.ausfuehren();
				} catch (RegelVerstoss rv) {
					throw new RuntimeException(rv);
				}
				
				Spieler sp = ze.getSpieler();
				int nummer = spiel.getSpieler().indexOf(sp) + 1;
				System.out.println();
				System.out.println(sp.getName() + " (" + nummer + ")" +
				                   " hat mit " + ze.getKarte() + " folgendes gespielt:");
				System.out.println();
				zeichneBrett();
				System.out.println();
				
			} else if (nachricht instanceof ChatNachricht) {
				ChatNachricht cn = (ChatNachricht) nachricht;
				System.out.println("> " + cn);
			} else if (nachricht instanceof VerbindungGeschlossen) {
				System.out.println("Die Verbindung wurde durch den Server unerwartet geschlossen!");
				System.exit(99);
			} else {
				/* TODO: Platzhalter */
				System.out.println("Unerwartete Nachricht " + nachricht);
				System.exit(99);
			}
		}
	}


	public static void main(String[] args) throws UnknownHostException, IOException, VerbindungWegException {

		String hostname = "localhost";
		String spielerName = null;
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
			EventQueue queue = new EventQueue();
			server = new EndPunkt(hostname, port, new BriefkastenAdapter(queue));
			
			System.out.println();
			
			if (spielerName == null) {
				System.out.print("Bitte gib deinen Namen an: ");
				spielerName = liesStringEin();
			}
			
			server.sende(new SpielBeitreten(spielerName));
			
			SpielStartNachricht startNachricht;
			while (true) {
				/* FIXME: foldgender hässlciher code wird bald im statemachine verschwinden */
				Brief brief = ((NetzwerkEvent) queue.dequeue()).brief;
				Nachricht nachricht = brief.nachricht; 
	
				if (nachricht instanceof SpielStartNachricht) {
					startNachricht = (SpielStartNachricht) brief.nachricht;
					break;
				} else if (nachricht instanceof SpielVollNachricht) {
					System.out.println("Leider ist das Spiel bereits voll.");
					System.exit(1);
				} else if (nachricht instanceof ChatNachricht) {
					System.out.println("> " + nachricht);
				} else if (nachricht instanceof VerbindungGeschlossen) {
					System.out.println("Die Verbindung wurde durch den Server unerwartet geschlossen!");
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
				System.out.println((i+1) + ") " + startNachricht.spieler[i]);
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
				
			Prototyp spielBrett = new Prototyp(spiel, spielerIch, server, queue);
			spielBrett.run();
		
		} catch (ConnectException e) {
			System.out.println("Verbindung zum Server konnte nicht aufgebaut werden.");
		} 
		catch (VerbindungWegException v) {
			System.out.println("Die Verbindung zum Server wurde unerwartetet abgebrochen!");
			v.printStackTrace();
		} finally {
			/* So oder so wenn wir hier fertig sind machen wir den Socket zu. */
			if (server != null) {
				server.ausschalten(); /* FIXME: etwaige Exceptions in diesem Code können wir in der finalen Version ignorieren */
			}
		}
	}
}

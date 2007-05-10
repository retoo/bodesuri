package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import pd.brett.BankFeld;
import pd.brett.Brett;
import pd.brett.Feld;
import pd.brett.WegFeld;
import pd.deck.Karte;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Spiel;
import pd.zugsystem.Zug;
import dienste.netzwerk.Brief;
import dienste.netzwerk.Briefkasten;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.VerbindungWegException;
import dienste.netzwerk.nachrichten.ChatNachricht;
import dienste.netzwerk.nachrichten.Nachricht;
import dienste.netzwerk.nachrichten.NeueVerbindung;
import dienste.netzwerk.nachrichten.SpielBeitreten;
import dienste.netzwerk.nachrichten.SpielStartNachricht;
import dienste.netzwerk.nachrichten.SpielVollNachricht;
import dienste.netzwerk.nachrichten.ZugAufforderung;
import dienste.netzwerk.nachrichten.ZugInformation;

public class Prototyp {
	private Spiel spiel;
	private Brett brett;

	private BankFeld startFeld;
	private EndPunkt server;
	private Spieler lokalerSpieler;

	public Prototyp(Spiel spiel, Spieler spielerIch, EndPunkt server) {
		this.server = server;
		this.spiel = spiel;
		this.lokalerSpieler = spielerIch;
		this.brett = spiel.getBrett();
		this.startFeld = brett.getBankFeldVon(spiel.getSpieler().get(0));
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
		for (Feld feld : startFeld.getWeg(startFeld.getVorheriges())) {
			zeichneFeld(feld);
		}
		System.out.println();
	}

	public void zeichneFeld(Feld feld) {
		if (feld.istBesetzt()) {
			Spieler spieler = feld.getFigur().getSpieler();
			System.out.print(spiel.getSpieler().indexOf(spieler) + 1);
		} else if (feld instanceof BankFeld) {
			System.out.print("X");
		} else if (feld instanceof WegFeld) {
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
			
			if (1 <= eingabe || eingabe <= karten.size()) {
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
	
	private Zug erfasseZug(List<Karte> karten) {
	    Karte karte = auswahlKarte(karten);
	    Feld start = auswahlStartfeld();
	    Feld ziel  = auswahlZielfeld();
	    
	    Bewegung bewegung = new Bewegung(start, ziel);
	    Zug zug = new Zug(lokalerSpieler, karte, bewegung);
	    return zug;
    }


	private void run() throws VerbindungWegException {
		Briefkasten bk = server.briefkasten;
		
		begruessungAusgaben();
		System.out.println();
		zeichneBrett();
		System.out.println();
		
		while (true) {
			// zeichneBrett();
			
			Brief b = bk.getBrief();
			Nachricht nachricht = b.nachricht;	
			
			if (nachricht instanceof ZugAufforderung) {
				System.out.println();
				System.out.println(lokalerSpieler.getName() + ", du bist am Zug!");
				System.out.println();
				
				List<Karte> karten = spiel.getKartenGeber().getKarten(5);
				
				while (true) {
					Zug zug = erfasseZug(karten);
				
					if (zug.validiere()) {
						server.sende(new ZugInformation(zug));
						break;
					} else {
						System.out.println();
						System.out.println("Ungültiger Zug, bitte erneut versuchen.");
						System.out.println();
					}
				}
				
			} else if (nachricht instanceof ZugInformation) {
				ZugInformation zn = (ZugInformation) nachricht;
				
				if (zn.zug.validiere()) {
					zn.zug.ausfuehren();
				} else {
					throw new RuntimeException("Ungültiger Zug " + zn.zug);
				}
				
				Spieler sp = zn.zug.getSpieler();
				int nummer = spiel.getSpieler().indexOf(sp) + 1;
				System.out.println();
				System.out.println(sp.getName() + " (" + nummer + ")" +
				                   " hat folgendes gespielt:");
				System.out.println();
				zeichneBrett();
				System.out.println();
				
			} else if (nachricht instanceof ChatNachricht) {
				ChatNachricht cn = (ChatNachricht) nachricht;
				System.out.println("> " + cn);
				
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
			server = new EndPunkt(hostname, port);
		} catch (ConnectException e) {
			System.out.println("Verbindung zum Server konnte nicht aufgebaut werden.");
			System.exit(1);
		}
		System.out.println();
		
		if (spielerName == null) {
			System.out.print("Bitte gib deinen Namen an: ");
			spielerName = liesStringEin();
		}
		
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
			
		Prototyp spielBrett = new Prototyp(spiel, spielerIch, server);
		spielBrett.run();
	}
}

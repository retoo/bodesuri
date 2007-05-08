package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import dienste.netzwerk.Brief;
import dienste.netzwerk.Briefkasten;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.nachrichtentypen.ChatNachricht;
import dienste.netzwerk.nachrichtentypen.Nachricht;
import dienste.netzwerk.nachrichtentypen.SpielBeitreten;
import dienste.netzwerk.nachrichtentypen.SpielStartNachricht;
import dienste.netzwerk.nachrichtentypen.SpielVollNachricht;
import dienste.netzwerk.nachrichtentypen.VerbindungWegException;
import dienste.netzwerk.nachrichtentypen.ZugAufforderung;
import dienste.netzwerk.nachrichtentypen.ZugInformation;

import pd.deck.Acht;
import pd.deck.Ass;
import pd.deck.Dame;
import pd.deck.Drei;
import pd.deck.Fuenf;
import pd.deck.Herz;
import pd.deck.Karte;
import pd.deck.KartenGeber;
import pd.deck.Koenig;
import pd.deck.Neun;
import pd.deck.Sechs;
import pd.deck.Vier;
import pd.deck.Zehn;
import pd.deck.Zwei;
import pd.spielerverwaltung.Spieler;
import pd.zugsystem.BankFeld;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Brett;
import pd.zugsystem.Feld;
import pd.zugsystem.Spiel;
import pd.zugsystem.WegFeld;
import pd.zugsystem.Zug;

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
		System.out.println("Spieler: " + lokalerSpieler);
		System.out.println();
		for (Feld feld : startFeld.getWeg(startFeld.getVorheriges())) {
			zeichneFeld(feld);
		}
		System.out.println();
		System.out.println();
	}

	public void zeichneFeld(Feld feld) {
		if (feld.istBesetzt()) {
			Spieler spieler = feld.getFigur().getSpieler();
			System.out.print(spiel.getSpieler().indexOf(spieler));
		} else if (feld instanceof BankFeld) {
			System.out.print("X");
		} else if (feld instanceof WegFeld) {
			System.out.print("-");
		}

	}

	public void begruessungAusgaben() {
		System.out.println("Willkommen im Spiel Bodesuri (Prototyp).");
		System.out.println("(Legende: X = Bänkli, - = normales Feld, " +
		                   "Zahl = Spieler)");
	}

	public Spieler auswahlSpieler() {
		System.out.println("Wähle einen Spieler aus [0-3]:");
		Spieler spieler = spiel.getSpieler().get(liesZahlEin());
		System.out.println(spieler + " wurde ausgewählt.");
		
		return spieler;
	}

	public Karte auswahlKarte() {
		System.out.print("Welche Karte möchtest du spielen: ");
		int eingabe = liesZahlEin();
		
		Herz herz = new Herz();

		switch (eingabe) {
		case 1: return new Ass(herz);
		case 2: return new Zwei(herz);
		case 3: return new Drei(herz);
		case 4: return new Vier(herz);
		case 5: return new Fuenf(herz);
		case 6: return new Sechs(herz);
		case 7: throw new RuntimeException("Sieben noch nicht möglich!");
		case 8: return new Acht(herz);
		case 9: return new Neun(herz);
		case 10: return new Zehn(herz);
		case 11: return new Ass(herz);
		case 12: return new Dame(herz);
		case 13: return new Koenig(herz);
		default:
			/*
			 * TODO: muss noch schöner werden, z.b. wiederholte eingabe wenn die
			 * Karte unbeannt ist
			 */
			throw new RuntimeException("Unbekannte Karte");
		}
	}

	public Feld auswahlStartfeld() {
		System.out.print("Gib das Feld der zu spielenden Figur ein: ");
		return startFeld.getNtesFeld(liesZahlEin());
	}

	public Feld auswahlZielfeld() {
		System.out.print("Gib das Zielfeld der Figur ein: ");
		return startFeld.getNtesFeld(liesZahlEin());
	}

	private int liesZahlEin() {
		while (true) {
			try {
				return Integer.parseInt(liesStringEin());
			} catch (NumberFormatException e) {
				/* TODO: prüfen ob das so okay ist */
				System.out.print("Ungültige Zahl, btte erneut versuchen: ");
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

	public void zeichneKarten(Spieler spieler) {
		// spieler.getKarten();
	}

	public boolean ausfuerenZug(Zug zug) {
		if (zug.validiere()) {
			System.out.println("Führe Zug aus: " + zug);
			zug.ausfuehren();
			return true;
		} else {
			zug.verwerfen();
			System.out.println("\nFehler: Der Spielzug entspricht nicht " +
			                   "der gespielten Karte.\n");
			return false;
		}
	}
	
	private Zug erfasseZug(Spieler spieler) {
	    Karte karte = auswahlKarte();
	    Feld start = auswahlStartfeld();
	    Feld ziel  = auswahlZielfeld();
	    
	    Bewegung bewegung = new Bewegung(start, ziel);
	    Zug zug = new Zug(spieler, karte, bewegung);
	    return zug;
    }



	private void run() throws VerbindungWegException {
		Briefkasten bk = server.briefkasten;
		begruessungAusgaben();

		
		while (true) {
			zeichneBrett();
			
			Brief b = bk.getBrief();
			Nachricht nachricht = b.nachricht;
			
	
			
			if (nachricht instanceof ZugAufforderung) {
				System.out.println("UUh.. wir sind an der Reihe, HOPP!");
				
				
				while (true) {
					Zug zug = erfasseZug(lokalerSpieler);
				
					if (zug.validiere()) {
						server.sende(new ZugInformation(zug));
						break;
					} else {
						System.out.println("Huere michi, de zug isch scheiss ungültig");
					}
				}
				
				
			} else if (nachricht instanceof ZugInformation) {
				ZugInformation zn = (ZugInformation) nachricht;
				System.out.println("Zug erhalten " + zn.zug);
				boolean res = ausfuerenZug(zn.zug);
				
				if (!res) {
					throw new RuntimeException("Ungültiger Zug " + zn.zug); 
				}
				
				System.out.println("Zug ausgeführt");
			} else if (nachricht instanceof ChatNachricht) {
				ChatNachricht cn = (ChatNachricht) nachricht;
				
				System.out.println(" > " + b.absender + ": " + cn);
				
			} else {
				/* TODO: Platzhalter */
				System.out.println("Unerwartete Nachricht " + nachricht);
				System.exit(99);
			}
		}
	}


	public static void main(String[] args) throws UnknownHostException, IOException, VerbindungWegException {
		System.out.println("Bitte gib deinen Namen an!");
		String spielerName = liesStringEin();
		
		String hostname = "localhost";
		int port = 3334;
		if (args.length >= 1) {
			hostname = args[0];
			if (args.length == 2) {
				port = Integer.parseInt(args[1]);
			}
		}
		
		EndPunkt server = new EndPunkt(hostname, port);
		
		server.sende(new SpielBeitreten(spielerName));
		
		SpielStartNachricht startNachricht;
		while (true) {
			Brief b = server.briefkasten.getBrief();
			Nachricht nachricht = b.nachricht; 

			if (nachricht instanceof SpielStartNachricht) {
				startNachricht = (SpielStartNachricht) b.nachricht;
				break;
			} else if (nachricht instanceof SpielVollNachricht) {
				System.out.println("Sorry, Spiel ist bereits voll");
				System.exit(0);
			} else {
				/* TODO: Platzhalter */
				System.out.println("Unbekannte Nachricht " + nachricht);	
			}
		}

		System.out.println("Das Spiel beginnt... ");
		System.out.println();
		
		System.out.println("Die Spieler sind:");
		for (String name : startNachricht.spieler ) {
			System.out.println(" - " + name);
		}
		
		Spiel spiel = new Spiel();
		KartenGeber kartenGeber = new KartenGeber();
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

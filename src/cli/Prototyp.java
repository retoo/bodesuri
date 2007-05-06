package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pd.deck.Acht;
import pd.deck.Ass;
import pd.deck.Dame;
import pd.deck.Drei;
import pd.deck.Fuenf;
import pd.deck.Herz;
import pd.deck.Karte;
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

	public Prototyp(Spiel spiel) {
		this.spiel = spiel;
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
		System.out.println();
		for (Feld feld : startFeld.getWeg(startFeld.getVorheriges())) {
			zeichneFeld(feld);
		}
		System.out.println();
		System.out.println();
	}

	public void zeichneFeld(Feld feld) {
		if (feld.istBesetzt()) {
			System.out.print(feld.getFigur().getSpieler().getNummer());
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
				return Integer.parseInt(eingabeAuswerten());
			} catch (NumberFormatException e) {
				/* TODO: prüfen ob das so okay ist */
				System.out.print("Ungültige Zahl, btte erneut versuchen: ");
			}
		}
	}

	private String eingabeAuswerten() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String eingabe = "";
		try {
			if ((eingabe = in.readLine()) != "") {
				return eingabe;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eingabe;
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

	private void ausfuehrenRunde(Spieler spieler) {
		while (true) {
			Zug zug = erfasseZug(spieler);
			
			if (ausfuerenZug(zug))
				return;
		}
	}

	private void run() {
		begruessungAusgaben();
		zeichneBrett();
		while (true) {
			Spieler spieler = auswahlSpieler();
			ausfuehrenRunde(spieler);
			zeichneBrett();
		}
	}

	public static void main(String[] args) {		
		Spiel spiel = new Spiel();
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu(new Spieler("Nr. " + i, i));
		}
		spiel.brettAufstellen();
		Prototyp prototyp = new Prototyp(spiel);
		prototyp.run();
	}
}

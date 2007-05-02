package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import pd.deck.Acht;
import pd.deck.Ass;
import pd.deck.Dame;
import pd.deck.Drei;
import pd.deck.Fuenf;
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
import pd.zugsystem.Zug;

public class Prototyp {
	private Spiel spiel;

	private Brett brett;

	private Spieler spieler;

	private BankFeld bankFeld;

	private Vector<Feld> felder;


	public Prototyp(Spiel spiel) {
		this.spiel = spiel;
		this.brett = spiel.getBrett();
		this.spieler = spiel.getSpieler().get(0);
		bankFeld = brett.getBankFeldVon(spieler);
		bankFeld.setFigur(spieler.getFiguren().get(0));
		felder = new Vector<Feld>();
		Feld feld = bankFeld;
		for (int i = 0; i <= 32; i++) {
			felder.add(feld);
			feld = feld.getNaechstes();
		}
	}

	public void zeichneBrett() {
		System.out.println();
		for (Feld feld : felder) {
			zeichneFeld(feld);
		}
		System.out.println();
		System.out.println();
	}

	public void zeichneFeld(Feld feld) {
		if (feld.getFigur() != null) {
			System.out.print("1");
		} else if (feld instanceof BankFeld) {
			System.out.print("X");
		} else {
			System.out.print("0");
		}
	}

	public void begruessungAusgaben() {
		System.out.println("Willkommen im Spiel Bodesuri (Prototyp).");
		System.out
				.println("(Legende: x = Bänkli, 0 = normales Feld, Zahl = Spieler)");
	}

	public void auswahlSpieler() {
		System.out.println("Wähle eine Spieler aus.");
		System.out.println("Spielfigur [1 - 4]: ");
		this.spieler = spiel.getSpieler().get(liesZahlEin());
		System.out.println(spieler.getName() + " wurde ausgewählt.");
	}

	public Karte auswahlKarte() {
		Karte karte;

		System.out.print("Gib die zu Spielende Karte ein: ");

		int eingabe = liesZahlEin();

		/* TODO: Robin: apfel-shift-f macht diesen block hier futsch, flick das :)) */
		switch (eingabe) {
		case 1:
			karte = new Ass();
			break;
		case 2:
			karte = new Zwei();
			break;
		case 3:
			karte = new Drei();
			break;
		case 4:
			karte = new Vier();
			break;
		case 5:
			karte = new Fuenf();
			break;
		case 6:
			karte = new Sechs();
			break;
		case 7: {
			throw new RuntimeException("Sieben noch nicht möglich!");
		}
		case 8:
			karte = new Acht();
			break;
		case 9:
			karte = new Neun();
			break;
		case 10:
			karte = new Zehn();
			break;
		case 11:
			karte = new Ass();
			break;
		case 12:
			karte = new Dame();
			break;
		case 13:
			karte = new Koenig();
			break;
		default: {
			/*
			 * TODO: muss noch schöner werden, z.b. wiederholte eingabe wenn die
			 * Karte unbeannt ist
			 */
			throw new RuntimeException("Unbekannte Karte");
		}
		}

		System.out.println("Die Karte " + karte + " wurde gespielt.\n");

		return karte;
	}

	public void auswahlFigur() {
		System.out.println("Wähle eine Figur aus.");
	}

	public Feld auswahlStartfeld() {
		System.out.print("Gib das Feld der zu spielenden Figur ein: ");
		return felder.get(liesZahlEin());
	}

	public Feld auswahlZielfeld() {
		System.out.print("Gib das Zielfeld der Figur ein: ");
		return felder.get(liesZahlEin());
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

	public boolean ausfuerenZug(Karte karte, Feld start, Feld ziel) {
		Bewegung bewegung = new Bewegung(start, ziel);
		Zug zug = new Zug(spieler, karte, bewegung);
		if (zug.validiere()) {
			System.out.println("Führe Zug aus: " + zug);
			zug.ausfuehren();
			return true;
		} else {
			zug.verwerfen();
			System.out
			.println("\nFehler: Der Spielzug entspricht nicht der gespielten Karte.\n");
			return false;
		}
	}

	private void ausfuehrenRunde() {
		while (true) {
			Karte karte = auswahlKarte();
			Feld start = auswahlStartfeld();
			Feld ziel = auswahlZielfeld();
			if (ausfuerenZug(karte, start, ziel))
				return;
		}

	}

	private void run() {
		begruessungAusgaben();
		zeichneBrett();
		while (true) {
			ausfuehrenRunde();
			zeichneBrett();
		}
	}

	public static void main(String[] args) {
		Spiel spiel = new Spiel();
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu(new Spieler("Spieler" + i));
		}
		spiel.brettAufstellen();
		Prototyp spielBrett = new Prototyp(spiel);
		spielBrett.run();
	}

}

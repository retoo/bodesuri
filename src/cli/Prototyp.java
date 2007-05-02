package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
import pd.zugsystem.WegFeld;
import pd.zugsystem.Zug;

public class Prototyp {
	private Spiel spiel;

	private Brett brett;

	private Spieler spieler;

	private BankFeld bankFeld;

	private Feld feld;

	private boolean aufstellung;

	public Prototyp(Spiel spiel) {
		this.spiel = spiel;
		this.brett = spiel.getBrett();
		this.aufstellung = false;
	}

	public void zeichneBrett() {
		System.out.println();
		this.spieler = spiel.getSpieler().get(0);
		bankFeld = brett.getBankFeldVon(spieler);
		bankFeld.setFigur(spieler.getFiguren().get(0));
		this.feld = bankFeld;
		for (int anzSpieler = 0; anzSpieler < 4; anzSpieler++) {
			for (int felder = 0; felder < 16; felder++) {

				if (!this.aufstellung) {
					if (feld instanceof BankFeld) {
						this.spieler = spiel.getSpieler().get(anzSpieler);
						feld.setFigur(spieler.getFiguren().get(0));
					}
				}
				zeichneFeld(feld);
				feld = feld.getNaechstes();
			}
		}
		System.out.println();
		System.out.println();
		this.aufstellung = true;
	}

	public void zeichneFeld(Feld feld) {
		if (feld.getFigur() != null) {
			for (int i = 0; i < 4; i++) {
				this.spieler = spiel.getSpieler().get(i);
				if (feld.getFigur().getSpieler() == spieler) {
					System.out.print(spieler.getNummer());
				}
			}
		} else if (feld instanceof BankFeld) {
			System.out.print("X");
		} else if (feld instanceof WegFeld) {
			System.out.print("-");
		}

	}

	public void begruessungAusgaben() {
		System.out.println("Willkommen im Spiel Bodesuri (Prototyp).");
		System.out
				.println("(Legende: x = Bänkli, 0 = normales Feld, Zahl = Spieler)");
	}

	public void auswahlSpieler() {
		System.out.println("Wähle eine Spieler aus.");
		System.out.println("Spielfigur [0 - 3]: ");
		this.spieler = spiel.getSpieler().get(liesZahlEin());
		System.out.println(spieler.getName() + " wurde ausgewählt.");
	}

	public Karte auswahlKarte() {
		Karte karte;

		System.out.print(spieler.getName()
				+ " gib die zu Spielende Karte ein: ");

		int eingabe = liesZahlEin();

		/*
		 * TODO: Robin: apfel-shift-f macht diesen block hier futsch, flick das
		 * :))
		 */
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

		System.out.println("Spieler " + spieler.getName()
				+ " spielt die Karte " + karte + ".\n");

		return karte;
	}

	public void auswahlFigur() {
		System.out.println("Wähle eine Figur aus.");
	}

	public Feld auswahlStartfeld() {
		System.out.print("Gib das Feld der zu spielenden Figur ein: ");
		// System.out.println(feld.getNtesFeld(liesZahlEin()).getFigur()
		// .getSpieler());
		return feld.getNtesFeld(liesZahlEin());
	}

	public Feld auswahlZielfeld() {
		System.out.print("Gib das Zielfeld der Figur ein: ");
		return feld.getNtesFeld(liesZahlEin());
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
			auswahlSpieler();
			ausfuehrenRunde();
			zeichneBrett();
		}
	}

	public static void main(String[] args) {
		Spiel spiel = new Spiel();
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu(new Spieler("Spieler" + i, i));
		}
		spiel.brettAufstellen();
		Prototyp spielBrett = new Prototyp(spiel);
		spielBrett.run();
	}
}

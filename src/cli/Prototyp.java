package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import pd.deck.Karte;
import pd.deck.Vier;
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

	private Feld startFeld;

	private Feld zielFeld;

	private Vector<Feld> felder;

	private Bewegung bewegung;

	private Zug zug;

	private Karte karte;

	public Prototyp(Spiel spiel) {
		this.spiel = spiel;
		this.brett = spiel.getBrett();
		this.spieler = spiel.getSpieler().get(0);
		bankFeld = brett.getBankFeldVon(spieler);
		bankFeld.setFigur(spieler.getFiguren().get(0));
		felder = new Vector<Feld>();
		Feld feld = bankFeld;
		for (int i = 0; i <= 10; i++) {
			felder.add(feld);
			feld = feld.getNaechstes();
		}
	}

	public void zeichneBrett() {
		System.out.println("\n===========");
		for (Feld feld : felder) {
			zeichneFeld(feld);
		}
		System.out.println("\n===========\n");
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
		this.spieler = spiel.getSpieler().get(
				Integer.parseInt(eingabeAuswerten()));
		System.out.println(spieler.getName() + " wurde ausgewählt.");
	}

	public void auswahlKarte() {
		System.out.print("Gib die zu Spielende Karte ein: ");
		if (Integer.parseInt(eingabeAuswerten()) == 4) {
			this.karte = new Vier();
		}
		System.out.println("Die Karte " + this.karte.getWert()
				+ " wurde gespielt.\n");
	}

	public void auswahlFigur() {
		System.out.println("Wähle eine Figur aus.");
	}

	public void auswahlStartfeld() {
		System.out.print("Gib das Feld der zu spielenden Figur ein: ");
		this.startFeld = felder.get(Integer.parseInt(eingabeAuswerten()));
	}

	public void auswahlZielfeld() {
		System.out.print("Gib das Zielfeld der Figur ein: ");
		this.zielFeld = felder.get(Integer.parseInt(eingabeAuswerten()));
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

	public void ausfuerenZug() {
		this.bewegung = new Bewegung(this.startFeld, this.zielFeld);
		this.zug = new Zug(spieler, karte, bewegung);
		if (zug.validiere() == true) {
			zug.ausfuehren();
		} else {
			System.out.println("\nFehler: Der Spielzug entspricht nicht der gespielten Karte.\n");
			ausfuehrenRunde();
		}
	}

	private void ausfuehrenRunde() {
		auswahlKarte();
		auswahlStartfeld();
		auswahlZielfeld();
		ausfuerenZug();
	}

	public static void main(String[] args) {
		Spiel spiel = new Spiel();
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu(new Spieler("Spieler" + i));
		}
		spiel.brettAufstellen();
		Prototyp spielBrett = new Prototyp(spiel);
		spielBrett.begruessungAusgaben();
		spielBrett.zeichneBrett();
		while(true){
			spielBrett.ausfuehrenRunde();
			spielBrett.zeichneBrett();			
		}
	}
}

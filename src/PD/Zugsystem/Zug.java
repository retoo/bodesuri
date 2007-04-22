package PD.Zugsystem;

import PD.Deck.Karte;
import PD.Spielerverwaltung.Spieler;

public class Zug {
	private Spieler spieler;
	private Karte karte;
	private Bewegung bewegung;

	public Zug(Spieler spieler, Karte karte, Bewegung bewegung) {
		this.spieler = spieler;
		this.karte = karte;
		this.bewegung = bewegung;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public Karte getKarte() {
		return karte;
	}

	public Bewegung getBewegung() {
		return bewegung;
	}
}

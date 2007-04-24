package PD.zugsystem;

import PD.spielerverwaltung.Spieler;

public class Figur {
	private Spieler spieler;

	public Figur(Spieler spieler) {
		this.spieler = spieler;
	}

	public Spieler getSpieler() {
    	return spieler;
    }
}

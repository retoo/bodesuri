package pd.zugsystem;

import java.io.Serializable;

import pd.karten.Karte;
import pd.spieler.Spieler;

public class ZugEingabe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Spieler spieler;
	private Karte karte;
	private Bewegung bewegung;
	
	public ZugEingabe(Spieler spieler, Karte karte, Bewegung bewegung) {
		this.spieler = spieler;
		this.karte = karte;
		this.bewegung = bewegung;
	}

	public Zug validiere() {
		return karte.getRegel().validiere(this);
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

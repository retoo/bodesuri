package pd.zugsystem;

import java.io.Serializable;

import pd.Spiel;
import pd.karten.Karte;
import pd.regelsystem.Regel;
import pd.regelsystem.RegelVerstoss;
import pd.spieler.Spieler;

public class ZugEingabe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Spiel spiel;
	private Spieler spieler;
	private Karte karte;
	private Bewegung bewegung;
	
	public ZugEingabe(Spiel spiel, Spieler spieler,
	                  Karte karte, Bewegung bewegung) {
		this.spiel = spiel;
		this.spieler = spieler;
		this.karte = karte;
		this.bewegung = bewegung;
	}

	public Zug validiere() throws RegelVerstoss {
		Regel regel = karte.getRegel();
		if (regel == null) {
			throw new RegelVerstoss("Karte ist noch nicht spielbar.");
		}
		return regel.validiere(this);
	}

	public Spiel getSpiel() {
		return spiel;
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

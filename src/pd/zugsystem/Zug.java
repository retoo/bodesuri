package pd.zugsystem;

import pd.deck.Karte;
import pd.regelsystem.Regel;
import pd.spielerverwaltung.Spieler;

public class Zug {
	private Spieler spieler;
	private Karte karte;
	private Bewegung bewegung;
	
	private Regel validierteRegel;

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
	
	public boolean validiere() {
		validierteRegel = karte.getRegel().validiere(this);
		return validierteRegel != null;
	}
	
	public void ausfuehren() {
		if (validierteRegel != null) {
			validierteRegel.ausfuehren(this);
		}
	}

	/***
	 * Der Zug wurde vom UI verworfen und wird so in der Form nicht durchgeführt.
	 * TODO: Evtl. wird das gar nicht gebraucht. Es gibt zurzeit nur eine Stele wo die
	 * Methode aufgerufen wird (-rschuett)
	 */
	public void verwerfen() {
		// TODO Auto-generated method stub
	}
}

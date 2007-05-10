package pd.zugsystem;

import java.io.Serializable;

import pd.karten.Karte;
import pd.regelsystem.Regel;
import pd.spieler.Spieler;

public class Zug implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Spieler spieler;
	private Karte karte;
	private Bewegung bewegung;
	
	/* TODO: Besser machen */
	private transient Regel validierteRegel;

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

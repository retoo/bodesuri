package pd.zugsystem;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import pd.karten.Karte;
import pd.regelsystem.Regel;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spieler.Spieler;

/**
 * Enthält Eingaben für Validierung und anschliessende Erstellung des Zuges.
 */
public class ZugEingabe implements Serializable {
	private Spieler spieler;
	private Karte karte;
	private Karte konkreteKarte;
	private List<Bewegung> bewegungen;

	private ZugEingabe(Spieler spieler, Karte karte) {
		this.spieler = spieler;
		this.karte = karte;
		this.konkreteKarte = karte;
	}

	/**
	 * Erstellt eine Zugeingabe.
	 *
	 * @param spieler
	 *            Spieler, der diesen Zug ausführt
	 * @param karte
	 *            Karte, mit der gespielt wird
	 * @param bewegung
	 *            Bewegung (Start, Ziel) des Zuges
	 */
	public ZugEingabe(Spieler spieler, Karte karte, Bewegung bewegung) {
		this(spieler, karte);
		this.bewegungen = new Vector<Bewegung>();
		this.bewegungen.add(bewegung);
	}

	public ZugEingabe(Spieler spieler, Karte karte, List<Bewegung> bewegungen) {
		this(spieler, karte);
		this.bewegungen = bewegungen;
	}

	/**
	 * Validiert Zugeingabe.
	 *
	 * @see Regel#validiere(ZugEingabe)
	 *
	 * @return Zug, der ausgeführt werden kann
	 * @throws RegelVerstoss
	 *             Bei Regelwidrigkeit geworfen
	 */
	public Zug validiere() throws RegelVerstoss {
		Regel regel = konkreteKarte.getRegel();
		if (regel == null) {
			throw new RegelVerstoss("Karte ist noch nicht spielbar.");
		}
		return regel.validiere(this);
	}

	/*
	 * Ist nur beim Joker nötig.
	 */
	public void setKonkreteKarte(Karte konkreteKarte) {
		this.konkreteKarte = konkreteKarte;
	}

	/**
	 * @return Spieler, der diesen Zug ausführt
	 */
	public Spieler getSpieler() {
		return spieler;
	}
	
	public Spieler getBetroffenerSpieler() {
		return spieler.istFertig() ? spieler.getPartner() : spieler;
	}

	/**
	 * @return Karte, mit der gespielt wird
	 */
	public Karte getKarte() {
		return karte;
	}
	
	public Karte getKonkreteKarte() {
		return konkreteKarte;
	}

	/**
	 * @return Bewegung (Start, Ziel) des Zuges
	 */
	public Bewegung getBewegung() {
		return bewegungen.get(0);
	}

	public List<Bewegung> getBewegungen() {
		return bewegungen;
	}

	public int getAnzahlBewegungen() {
		return bewegungen.size();
	}

	public String toString() {
		return "ZugEingabe: " + getSpieler() + " mit " + getKarte() + " "
		       + getBewegung();
	}
	
	public String getKurzBeschreibung() {
		return getSpieler().getName() + " spielt " + getKarte();
	}
}

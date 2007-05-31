package pd.zugsystem;

import java.io.Serializable;

import pd.karten.Karte;
import pd.regelsystem.Regel;
import pd.regelsystem.RegelVerstoss;
import pd.spieler.Spieler;

/**
 * Enthält Eingaben für Validierung und anschliessende Erstellung des Zuges.
 */
public class ZugEingabe implements Serializable {
	private static final long serialVersionUID = 1L;

	private Spieler spieler;
	private Karte karte;
	private Bewegung bewegung;

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
		this.spieler = spieler;
		this.karte = karte;
		this.bewegung = bewegung;
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
		Regel regel = karte.getRegel();
		if (regel == null) {
			throw new RegelVerstoss("Karte ist noch nicht spielbar.");
		}
		return regel.validiere(this);
	}

	/**
	 * @return Spieler, der diesen Zug ausführt
	 */
	public Spieler getSpieler() {
		return spieler;
	}

	/**
	 * @return Karte, mit der gespielt wird
	 */
	public Karte getKarte() {
		return karte;
	}

	/**
	 * @return Bewegung (Start, Ziel) des Zuges
	 */
	public Bewegung getBewegung() {
		return bewegung;
	}

	public String toString() {
		return "ZugEingabe: " + getSpieler() + " mit "
		       + getKarte() + " " + getBewegung();
	}
}

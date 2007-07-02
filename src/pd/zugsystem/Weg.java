package pd.zugsystem;

import java.util.Vector;

import pd.spiel.brett.Feld;

/**
 * Ein Weg ist eine Liste von Feldern, die bei einer {@link Bewegung} "berührt"
 * werden.
 */
public class Weg extends Vector<Feld> {
	private boolean vorwaerts;

	public Weg(boolean vorwaerts) {
		this.vorwaerts = vorwaerts;
	}

	/**
	 * @return true, wenn der Weg rückwärts geht
	 */
	public boolean istRueckwaerts() {
		return !vorwaerts;
	}

	/**
	 * Berechne die Länge des Weges, also wie viele Schritte gebraucht werden,
	 * um von Start zum Ziel zu kommen.
	 * 
	 * <b>Achtung:</b> Ist nicht das gleiche wie size().
	 * 
	 * @return Länge des Weges
	 */
	public int getWegLaenge() {
		return size() - 1;
	}
}

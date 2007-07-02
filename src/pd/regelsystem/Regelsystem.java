package pd.regelsystem;

import java.util.LinkedList;
import java.util.List;

import pd.regelsystem.karten.Karte;
import pd.spiel.spieler.Spieler;

/**
 * Beinhaltet Methoden, die das Abfragen von Regeln mit den Daten eines Spielers
 * vereinfachen.
 */
public class Regelsystem {
	/**
	 * @param spieler Spieler, der überprüft wird
	 * @return true, wenn der Spieler mit seinen Karten ziehen kann
	 */
	public static boolean kannZiehen(Spieler spieler) {
		for (Karte karte : spieler.getKarten()) {
			Regel regel = karte.getRegel();
			if (regel != null && regel.istZugMoeglich(spieler)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param spieler Spieler, der überprüft wird
	 * @return Liste von allen möglichen Zügen als ZugEingaben
	 */
	public static List<ZugEingabe> getMoeglicheZuege(Spieler spieler) {
		List<ZugEingabe> moegliche = new LinkedList<ZugEingabe>();

		for (Karte karte : spieler.getKarten()) {
			Regel regel = karte.getRegel();
			if (regel != null) {
				moegliche.addAll(regel.getMoeglicheZuege(spieler, karte));
			}
		}

		return moegliche;
    }
}

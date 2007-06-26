package pd.regelsystem.verstoesse;

/**
 * Wird geworfen, wenn eine Regel ungültig ist, also die Zugeingabe gegen die
 * Regel verstösst.
 */
public class RegelVerstoss extends Exception {
	/**
	 * @param grund
	 *            Grund, warum der RegelVerstoss auftrat
	 */
	public RegelVerstoss(String grund) {
		super(grund);
	}

	/**
	 * Sieht zum Beispiel so aus: "RegelVerstoss: Zug muss mit Figur begonnen
	 * werden."
	 */
	public String toString() {
		return "Regelverstoss: " + getMessage();
	}
}

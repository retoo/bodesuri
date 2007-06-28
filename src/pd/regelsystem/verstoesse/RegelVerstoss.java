package pd.regelsystem.verstoesse;

/**
 * Wird geworfen, wenn eine Regel ungültig ist, also die Zugeingabe gegen die
 * Regel verstösst.
 */
public abstract class RegelVerstoss extends Exception {
	private double spezifitaet;

	/**
	 * @param meldung
	 *            Grund, warum der RegelVerstoss auftrat
	 */
	public RegelVerstoss(String meldung) {
		this(0.0, meldung);
	}

	public RegelVerstoss(double spezifitaet, String meldung) {
		super(meldung);
		this.spezifitaet = spezifitaet;
	}

	/**
	 * Sieht zum Beispiel so aus: "Regelverstoss: Zug muss mit Figur begonnen
	 * werden."
	 */
	public String toString() {
		return "Regelverstoss: " + getMessage();
	}

	/**
	 * @return "Spezifität" des Verstosses; je höher die Spezifität, desto
	 *         genauer ist der Verstoss
	 */
	public double getSpezifitaet() {
		return spezifitaet;
	}
}

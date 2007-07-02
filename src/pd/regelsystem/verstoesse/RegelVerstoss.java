package pd.regelsystem.verstoesse;

/**
 * Wird geworfen, wenn eine Regel ungültig ist, also die Zugeingabe gegen die
 * Regel verstösst.
 */
public abstract class RegelVerstoss extends Exception {
	private double spezifitaet;

	/**
	 * @param erklaerung Erklärung des Verstosses
	 */
	public RegelVerstoss(String erklaerung) {
		this(0.0, erklaerung);
	}

	/**
	 * @param spezifitaet Spezifität des Verstosses, ungefähr von 0.0 bis 2.0
	 * @param erklaerung Erklärung des Verstosses
	 */
	public RegelVerstoss(double spezifitaet, String erklaerung) {
		super(erklaerung);
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

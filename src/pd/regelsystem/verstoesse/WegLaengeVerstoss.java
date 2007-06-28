package pd.regelsystem.verstoesse;

/**
 * Verstoss, der gebraucht wird, wenn die Weglänge nicht gestimmt hat.
 */
public class WegLaengeVerstoss extends RegelVerstoss {
	private int sollLaenge;
	private int istLaenge;

	public WegLaengeVerstoss(int sollLaenge, int istLaenge) {
		super(1.0 / Math.abs(sollLaenge - istLaenge),
		      "Man muss " + sollLaenge +
              " und nicht " + istLaenge + " Felder weit fahren.");
		this.sollLaenge = sollLaenge;
		this.istLaenge  = istLaenge;
	}

	/**
	 * @return Wie lang der Weg in der Zugeingabe war
	 */
	public int getIstLaenge() {
		return istLaenge;
	}

	/**
	 * @return Wie lang der Weg sein sollte
	 */
	public int getSollLaenge() {
		return sollLaenge;
	}
}

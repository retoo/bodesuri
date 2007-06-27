package pd.regelsystem.verstoesse;

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

	public int getIstLaenge() {
		return istLaenge;
	}

	public int getSollLaenge() {
		return sollLaenge;
	}
}

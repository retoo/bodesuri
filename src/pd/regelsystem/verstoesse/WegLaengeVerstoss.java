package pd.regelsystem.verstoesse;

public class WegLaengeVerstoss extends RegelVerstoss {
	private int sollLaenge;
	private int istLaenge;

	public WegLaengeVerstoss(int sollLaenge, int istLaenge) {
		super("Zug muss über " + sollLaenge +
              " und nicht " + istLaenge + " Felder gehen.");
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

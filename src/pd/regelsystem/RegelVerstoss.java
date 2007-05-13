package pd.regelsystem;

public class RegelVerstoss extends Exception {
	private static final long serialVersionUID = 1L;

	public RegelVerstoss(String erklaerung) {
		super(erklaerung);
	}
	
	public String toString() {
		return "Regelverstoss: " + getMessage();
	}
}

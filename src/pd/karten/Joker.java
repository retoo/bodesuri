package pd.karten;

import pd.regelsystem.Regel;

public class Joker extends Karte {
	private static final long serialVersionUID = 1L;

	public Joker(KartenFarbe farbe) {
		super(farbe);
	}
	
	public Regel getRegel() {
		throw new RuntimeException("Noch nicht implementiert!");
	}
}

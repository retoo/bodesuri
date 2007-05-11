package pd.karten;

import pd.regelsystem.Regel;

public class Joker extends Karte {
	private static final long serialVersionUID = 1L;

	public Joker(KartenFarbe farbe, int deck) {
		super("Joker", farbe, deck);
	}
	
	public Regel getRegel() {
		throw new RuntimeException("Noch nicht implementiert!");
	}
}

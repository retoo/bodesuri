package pd.deck;

import pd.regelsystem.Regel;

public class Bube extends Karte{
	private static final long serialVersionUID = 1L;

	public Bube(KartenFarbe farbe) {
		super(farbe);
	}
	
	public Regel getRegel() {
		throw new RuntimeException("Noch nicht implementiert!");
	}
}

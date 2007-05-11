package pd.karten;

import pd.regelsystem.Regel;

public class Bube extends Karte{
	private static final long serialVersionUID = 1L;

	public Bube(KartenFarbe farbe, int deck) {
		super("Bube", farbe, deck);
	}
	
	public Regel getRegel() {
		throw new RuntimeException("Noch nicht implementiert!");
	}
}

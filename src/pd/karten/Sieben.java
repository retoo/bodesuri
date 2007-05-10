package pd.karten;

import pd.regelsystem.Regel;

public class Sieben extends Karte{
	private static final long serialVersionUID = 1L;

	public Sieben(KartenFarbe farbe) {
		super(farbe);
	}
	
	public Regel getRegel() {
		throw new RuntimeException("Noch nicht implementiert!");
	}
}

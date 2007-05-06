package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Zwei extends Karte {
	public Zwei(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(2));
	}
}

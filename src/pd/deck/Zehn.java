package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Zehn extends Karte {
	public Zehn(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(10));
	}
}

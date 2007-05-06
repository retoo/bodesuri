package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Fuenf extends Karte {
	public Fuenf(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(5));
		setWert(5);
	}
}

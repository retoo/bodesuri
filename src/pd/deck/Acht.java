package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Acht extends Karte {
	public Acht(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(8));
		setWert(5);
	}
}

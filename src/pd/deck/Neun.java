package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Neun extends Karte {
	public Neun(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(9));
	}
}

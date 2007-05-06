package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Sechs extends Karte {
	public Sechs(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(6));
	}
}

package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Dame extends Karte {
	public Dame(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(12));
	}
}

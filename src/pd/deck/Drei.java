package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Drei extends Karte {
	public Drei(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(3));
	}
}

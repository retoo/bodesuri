package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Zehn extends Karte {
	private static final long serialVersionUID = 1L;

	public Zehn(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(10));
	}
}

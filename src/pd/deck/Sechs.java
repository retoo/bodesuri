package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Sechs extends Karte {
	private static final long serialVersionUID = 1L;

	public Sechs(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(6));
	}
}

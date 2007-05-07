package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Dame extends Karte {
	private static final long serialVersionUID = 1L;

	public Dame(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(12));
	}
}

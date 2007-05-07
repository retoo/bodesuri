package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Drei extends Karte {
	private static final long serialVersionUID = 1L;

	public Drei(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(3));
	}
}

package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Zwei extends Karte {
	private static final long serialVersionUID = 1L;

	public Zwei(KartenFarbe farbe) {
		super(farbe);
		setRegel(new VorwaertsRegel(2));
	}
}

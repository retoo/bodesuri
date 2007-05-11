package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Fuenf extends Karte {
	private static final long serialVersionUID = 1L;

	public Fuenf(KartenFarbe farbe, int deck) {
		super("Fuenf", farbe, deck);
		setRegel(new VorwaertsRegel(5));
		setWert(5);
	}
}

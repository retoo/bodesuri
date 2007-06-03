package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Fuenf extends Karte {
	public Fuenf(KartenFarbe farbe, int deck) {
		super("Fuenf", farbe, deck);
		setRegel(new VorwaertsRegel(5));
	}
}

package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Fuenf extends Karte {
	public Fuenf(KartenFarbe farbe) {
		super("Fünf", farbe);
		setRegel(new VorwaertsRegel(5));
	}
}

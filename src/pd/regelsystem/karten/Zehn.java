package pd.regelsystem.karten;

import pd.regelsystem.VorwaertsRegel;

public class Zehn extends Karte {
	public Zehn(KartenFarbe farbe) {
		super("Zehn", farbe);
		setRegel(new VorwaertsRegel(10));
	}
}

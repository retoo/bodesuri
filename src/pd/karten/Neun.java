package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Neun extends Karte {
	public Neun(KartenFarbe farbe) {
		super("Neun", farbe);
		setRegel(new VorwaertsRegel(9));
	}
}

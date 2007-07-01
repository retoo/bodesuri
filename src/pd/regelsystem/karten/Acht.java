package pd.regelsystem.karten;

import pd.regelsystem.VorwaertsRegel;

public class Acht extends Karte {
	public Acht(KartenFarbe farbe) {
		super("Acht", farbe);
		setRegel(new VorwaertsRegel(8));
	}
}

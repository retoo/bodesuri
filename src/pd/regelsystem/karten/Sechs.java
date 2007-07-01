package pd.regelsystem.karten;

import pd.regelsystem.VorwaertsRegel;

public class Sechs extends Karte {
	public Sechs(KartenFarbe farbe) {
		super("Sechs", farbe);
		setRegel(new VorwaertsRegel(6));
	}
}

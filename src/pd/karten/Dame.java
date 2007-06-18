package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Dame extends Karte {
	public Dame(KartenFarbe farbe) {
		super("Dame", farbe);
		setRegel(new VorwaertsRegel(12));
	}
}

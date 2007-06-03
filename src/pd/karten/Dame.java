package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Dame extends Karte {
	public Dame(KartenFarbe farbe, int deck) {
		super("Dame", farbe, deck);
		setRegel(new VorwaertsRegel(12));
	}
}

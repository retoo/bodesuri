package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Zehn extends Karte {
	public Zehn(KartenFarbe farbe, int deck) {
		super("Zehn", farbe, deck);
		setRegel(new VorwaertsRegel(10));
	}
}

package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Neun extends Karte {
	public Neun(KartenFarbe farbe, int deck) {
		super("Neun", farbe, deck);
		setRegel(new VorwaertsRegel(9));
	}
}

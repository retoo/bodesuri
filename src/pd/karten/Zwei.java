package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Zwei extends Karte {
	public Zwei(KartenFarbe farbe, int deck) {
		super("Zwei", farbe, deck);
		setRegel(new VorwaertsRegel(2));
	}
}

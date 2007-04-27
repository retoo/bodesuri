package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Zwei extends Karte {
	public Zwei() {
		setRegel(new VorwaertsRegel(2));
	}
}

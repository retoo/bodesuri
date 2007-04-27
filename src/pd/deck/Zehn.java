package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Zehn extends Karte {
	public Zehn() {
		setRegel(new VorwaertsRegel(10));
	}
}

package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Neun extends Karte {
	public Neun() {
		setRegel(new VorwaertsRegel(9));
	}
}

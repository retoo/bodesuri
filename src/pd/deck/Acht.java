package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Acht extends Karte {
	public Acht() {
		setRegel(new VorwaertsRegel(8));
	}
}

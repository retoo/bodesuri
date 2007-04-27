package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Sechs extends Karte {
	public Sechs() {
		setRegel(new VorwaertsRegel(6));
	}
}

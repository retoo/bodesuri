package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Dame extends Karte {
	public Dame() {
		setRegel(new VorwaertsRegel(12));
	}
}

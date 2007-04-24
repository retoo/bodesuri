package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Vier extends Karte {
	public Vier() {
		regeln.add(new VorwaertsRegel(4));
	}
}

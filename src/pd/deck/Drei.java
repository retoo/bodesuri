package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Drei extends Karte {
	public Drei() {
		setRegel(new VorwaertsRegel(3));
	}
}

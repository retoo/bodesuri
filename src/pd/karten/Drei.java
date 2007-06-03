package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Drei extends Karte {
	public Drei(KartenFarbe farbe, int deck) {
		super("Drei", farbe, deck);
		setRegel(new VorwaertsRegel(3));
	}
}

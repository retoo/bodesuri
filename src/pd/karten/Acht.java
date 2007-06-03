package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Acht extends Karte {
	public Acht(KartenFarbe farbe, int deck) {
		super("Acht", farbe, deck);
		setRegel(new VorwaertsRegel(8));
	}
}

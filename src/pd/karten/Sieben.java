package pd.karten;

import pd.regelsystem.SiebnerRegel;

public class Sieben extends Karte {
	public Sieben(KartenFarbe farbe, int deck) {
		super("Sieben", farbe, deck);
		setRegel(new SiebnerRegel());
	}
}

package pd.regelsystem.karten;

import pd.regelsystem.SiebnerRegel;

public class Sieben extends Karte {
	public Sieben(KartenFarbe farbe) {
		super("Sieben", farbe);
		setRegel(new SiebnerRegel());
	}
}

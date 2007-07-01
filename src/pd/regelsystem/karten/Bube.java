package pd.regelsystem.karten;

import pd.regelsystem.TauschRegel;

public class Bube extends Karte {
	public Bube(KartenFarbe farbe) {
		super("Bube", farbe);
		setRegel(new TauschRegel());
	}
}

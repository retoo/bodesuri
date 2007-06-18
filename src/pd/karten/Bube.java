package pd.karten;

import pd.regelsystem.TauschRegel;

public class Bube extends Karte {
	public Bube(KartenFarbe farbe) {
		super("Bube", farbe);
		setRegel(new TauschRegel());
	}
}

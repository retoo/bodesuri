package pd.karten;

import pd.regelsystem.TauschRegel;

public class Bube extends Karte {
	public Bube(KartenFarbe farbe, int deck) {
		super("Bube", farbe, deck);
		setRegel(new TauschRegel());
	}
}

package pd.karten;

import pd.regelsystem.TauschRegel;

public class Bube extends Karte {
	private static final long serialVersionUID = 1L;

	public Bube(KartenFarbe farbe, int deck) {
		super("Bube", farbe, deck);
		setRegel(new TauschRegel());
	}
}

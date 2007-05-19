package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Acht extends Karte {
	private static final long serialVersionUID = 1L;

	public Acht(KartenFarbe farbe, int deck) {
		super("Acht", farbe, deck);
		setRegel(new VorwaertsRegel(8));
	}
}

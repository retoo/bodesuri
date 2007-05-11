package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Neun extends Karte {
	private static final long serialVersionUID = 1L;

	public Neun(KartenFarbe farbe, int deck) {
		super("Neun", farbe, deck);
		setRegel(new VorwaertsRegel(9));
	}
}

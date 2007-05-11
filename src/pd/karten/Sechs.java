package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Sechs extends Karte {
	private static final long serialVersionUID = 1L;

	public Sechs(KartenFarbe farbe, int deck) {
		super("Sechs", farbe, deck);
		setRegel(new VorwaertsRegel(6));
	}
}

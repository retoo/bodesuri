package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Dame extends Karte {
	private static final long serialVersionUID = 1L;

	public Dame(KartenFarbe farbe, int deck) {
		super("Dame", farbe, deck);
		setRegel(new VorwaertsRegel(12));
	}
}

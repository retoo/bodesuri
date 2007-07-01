package pd.regelsystem.karten;

import pd.regelsystem.VorwaertsRegel;

public class Zwei extends Karte {
	public Zwei(KartenFarbe farbe) {
		super("Zwei", farbe);
		setRegel(new VorwaertsRegel(2));
	}
}

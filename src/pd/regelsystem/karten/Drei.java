package pd.regelsystem.karten;

import pd.regelsystem.VorwaertsRegel;

public class Drei extends Karte {
	public Drei(KartenFarbe farbe) {
		super("Drei", farbe);
		setRegel(new VorwaertsRegel(3));
	}
}

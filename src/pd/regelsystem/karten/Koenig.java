package pd.regelsystem.karten;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.StartRegel;
import pd.regelsystem.VorwaertsRegel;

public class Koenig extends Karte {
	public Koenig(KartenFarbe farbe) {
		super("König", farbe);
		RegelVeroderung r = new RegelVeroderung();
		r.fuegeHinzu(new VorwaertsRegel(13));
		r.fuegeHinzu(new StartRegel());
		setRegel(r);
	}
}

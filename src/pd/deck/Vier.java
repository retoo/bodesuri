package pd.deck;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.RueckwaertsRegel;
import pd.regelsystem.VorwaertsRegel;

public class Vier extends Karte {
	public Vier(KartenFarbe farbe) {
		super(farbe);
		RegelVeroderung regelVeroderung = new RegelVeroderung();
		regelVeroderung.fuegeHinzu(new VorwaertsRegel(4));
		regelVeroderung.fuegeHinzu(new RueckwaertsRegel(4));
		setRegel(regelVeroderung);
		setWert(4);
	}
}

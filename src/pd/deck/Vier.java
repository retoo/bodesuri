package pd.deck;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.RueckwaertsRegel;
import pd.regelsystem.VorwaertsRegel;

public class Vier extends Karte {
	public Vier() {
		RegelVeroderung regelVeroderung = new RegelVeroderung();
		regelVeroderung.fuegeRegelHinzu(new VorwaertsRegel(4));
		regelVeroderung.fuegeRegelHinzu(new RueckwaertsRegel(4));
		setRegel(regelVeroderung);
	}
}

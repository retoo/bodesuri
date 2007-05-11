package pd.karten;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.RueckwaertsRegel;
import pd.regelsystem.VorwaertsRegel;

public class Vier extends Karte {
	private static final long serialVersionUID = 1L;

	public Vier(KartenFarbe farbe, int deck) {
		super("Vier", farbe, deck);
		RegelVeroderung regelVeroderung = new RegelVeroderung();
		regelVeroderung.fuegeHinzu(new VorwaertsRegel(4));
		regelVeroderung.fuegeHinzu(new RueckwaertsRegel(4));
		setRegel(regelVeroderung);
		setWert(4);
	}
}

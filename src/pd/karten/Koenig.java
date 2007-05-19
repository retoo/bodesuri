package pd.karten;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.StartRegel;
import pd.regelsystem.VorwaertsRegel;

public class Koenig extends Karte{
	private static final long serialVersionUID = 1L;

	public Koenig(KartenFarbe farbe, int deck){
		super("Koenig", farbe, deck);
		RegelVeroderung r = new RegelVeroderung();
		r.fuegeHinzu(new VorwaertsRegel(13));
		r.fuegeHinzu(new StartRegel());
		setRegel(r);
	}
}

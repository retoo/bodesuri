package pd.regelsystem;

import pd.karten.KartenFarbe;
import pd.karten.Vier;

public class RegelVeroderungTest extends RegelTestCase {
	private Regel viererRegel;
	
	protected void setUp() {
		super.setUp();
		viererRegel = new Vier(KartenFarbe.Herz, 0).getRegel();
	}
	
	public void testVier() throws RegelVerstoss {
		start = bank(0);
		ziel  = start.getNtesFeld(4);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(viererRegel);
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	public void testVierFalsch() {
		start = bank(0);
		ziel  = start.getNtesFeld(3);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(viererRegel);
		
		start = lager(0);
		sollteVerstossGeben(viererRegel);
	}

	public void testVierRueckwaerts() throws RegelVerstoss {
		start = bank(0).getNtesFeld(4);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(viererRegel);
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	public void testVierRueckwaertsFalsch() {
		start = bank(0).getNtesFeld(3);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(viererRegel);
	}
}

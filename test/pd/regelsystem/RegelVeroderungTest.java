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
		start = bankFeld1;
		ziel  = start.getNtesFeld(4);
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(viererRegel);
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
	}
	
	public void testVierFalsch() {
		start = bankFeld1;
		ziel  = start.getNtesFeld(3);
		lagerFeld1.versetzeFigurAuf(start);
		sollteVerstossGeben(viererRegel);
		
		start = lagerFeld1;
		sollteVerstossGeben(viererRegel);
	}

	public void testVierRueckwaerts() throws RegelVerstoss {
		start = bankFeld1.getNtesFeld(4);
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(viererRegel);
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
	}
	
	public void testVierRueckwaertsFalsch() {
		start = bankFeld1.getNtesFeld(3);
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(start);
		sollteVerstossGeben(viererRegel);
	}
}

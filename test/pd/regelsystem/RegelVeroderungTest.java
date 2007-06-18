package pd.regelsystem;

import pd.karten.KartenFarbe;
import pd.karten.Vier;

public class RegelVeroderungTest extends RegelTestCase {
	protected void setUp() {
		super.setUp();
		regel = new Vier(KartenFarbe.Herz).getRegel();
	}
	
	public void testVier() throws RegelVerstoss {
		start = bank(0);
		ziel  = start.getNtesFeld(4);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren();
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	public void testVierFalsch() {
		start = bank(0);
		ziel  = start.getNtesFeld(3);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
		
		start = lager(0);
		sollteVerstossGeben();
	}

	public void testVierRueckwaerts() throws RegelVerstoss {
		start = bank(0).getNtesFeld(4);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren();
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	public void testVierRueckwaertsFalsch() {
		start = bank(0).getNtesFeld(3);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
	}
}

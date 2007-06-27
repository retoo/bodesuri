package pd.regelsystem;

import pd.karten.KartenFarbe;
import pd.karten.Vier;
import pd.regelsystem.verstoesse.RegelVerstoss;

/**
 * Testet die Funktionalität der RegelVeroderung anhand
 * der Viererregel.
 */
public class RegelVeroderungTest extends RegelTestCase {
	protected void setUp() {
		super.setUp();
		regel = new Vier(KartenFarbe.Herz).getRegel();
	}
	
	/**
	 * Validiert die Viererregel, indem ein gültiger Vorwärtszug
	 * ausgeführt wird.
	 * @throws RegelVerstoss
	 */
	public void testVier() throws RegelVerstoss {
		start = bank(0);
		ziel  = start.getNtesFeld(4);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren();
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Validiert die Viererregel, indem ein Vorwärtszug ausgeführt
	 * wird, der einen Verstoss verursacht.
	 */
	public void testVierFalsch() {
		start = bank(0);
		ziel  = start.getNtesFeld(3);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
		
		start = lager(0);
		sollteVerstossGeben();
	}

	/**
	 * Validiert die Viererregel, indem ein gültiger Rückwärtszug
	 * ausgeführt wird.
	 * @throws RegelVerstoss
	 */
	public void testVierRueckwaerts() throws RegelVerstoss {
		start = bank(0).getNtesFeld(4);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren();
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Validiert die Viererregel, indem ein Rückwärtszug ausgeführt
	 * wird, der einen Verstoss verursacht.
	 */
	public void testVierRueckwaertsFalsch() {
		start = bank(0).getNtesFeld(3);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
	}
}

package pd;

import pd.spiel.Spiel;

/**
 * Testet die Funktionalität des Spiels.
 */
public class SpielTest extends ProblemDomainTestCase {
	protected void setUp() {
		super.setUp();
		spiel = new Spiel();
	}

	/**
	 * Prüft auf konsistentes Verhalten des Spielers innerhalb
	 * des Spiels. 
	 */
	public void testSpieler() {
		assertEquals(4, spiel.getSpieler().size());
		spiel.fuegeHinzu("Spieler 0");
		spiel.fuegeHinzu("Spieler 1");
		spiel.fuegeHinzu("Spieler 2");
		spiel.fuegeHinzu("Spieler 3");
		assertEquals(4, spiel.getSpieler().size());

		assertEquals(0, spieler(0).getNummer());
		assertEquals("Spieler 0", spieler(0).getName());
		assertEquals("Spieler 0", spieler(0).toString());
	}
}

package pd;

/**
 * Beinhaltet Tests für das Brett in der Problem-Domain.
 */
public class BrettTest extends ProblemDomainTestCase {
	protected void setUp() {
		super.setUp();
	}
	
	/**
	 * Prüft, ob alle Felder vorhanden sind.
	 */
	public void testGetAlleFelder() {
		assertEquals(96, brett.getAlleFelder().size());
	}
	
	/**
	 * Prüft, ob Freies Lagerfeld gefunden wird und ob keines
	 * gefunden wird, wenn alle Lagerfelder besetzt sind. 
	 */
	public void testGetFreiesLagerFeldVon() {
		try {
			brett.getFreiesLagerFeldVon(spieler(0));
			fail("Sollte RuntimeException geben");
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
		lager(0).versetzeFigurAuf(bank(0));
		assertEquals(lager(0), brett.getFreiesLagerFeldVon(spieler(0)));
	}
}

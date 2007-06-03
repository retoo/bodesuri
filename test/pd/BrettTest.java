package pd;

public class BrettTest extends ProblemDomainTestCase {
	protected void setUp() {
		super.setUp();
	}
	
	public void testGetAlleFelder() {
		assertEquals(96, brett.getAlleFelder().size());
	}
	
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


public class BrettTest extends ProblemDomainTestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testGetAlleFelder() {
		assertEquals(96, brett.getAlleFelder().size());
	}
	
	public void testGetFreiesLagerFeldVon() {
		try {
			brett.getFreiesLagerFeldVon(spieler1);
			fail("Sollte RuntimeException geben");
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
		lagerFeld1.versetzeFigurAuf(bankFeld1);
		assertEquals(lagerFeld1, brett.getFreiesLagerFeldVon(spieler1));
	}
}

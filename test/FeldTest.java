import pd.brett.Feld;

public class FeldTest extends ProblemDomainTestCase {
	private Feld feld1;
	private Feld feld2;
	
	protected void setUp() throws Exception {
		super.setUp();
		feld1 = brett.getBankFeldVon(spieler1);
		lagerFeld1.versetzeFigurAuf(feld1);
		feld2 = feld1.getNaechstes();
	}

	public void testNaechstesVorheriges() {
		Feld feld = feld1.getNaechstes().getVorheriges();
		assertEquals(feld1, feld);
	}

	public void testBesetzt() {
		assertTrue(feld1.istBesetzt());
		assertTrue(feld1.istBesetztVon(spieler1));
		assertFalse(feld2.istBesetzt());
		assertFalse(feld2.istBesetztVon(spieler1));
	}
}

import pd.brett.Feld;

public class FeldTest extends ProblemDomainTestCase {
	private Feld feld1;
	private Feld feld2;
	
	protected void setUp() throws Exception {
		super.setUp();
		feld1 = bankFeld1;
		feld2 = feld1.getNaechstes();
		lagerFeld1.versetzeFigurAuf(feld1);
	}

	public void testNaechstesVorheriges() {
		Feld feld = feld1.getNaechstes().getVorheriges();
		assertEquals(feld1, feld);
	}
	
	public void testGetNtesFeld() {
		assertEquals(feld1, feld1.getNtesFeld(0));
		assertEquals(feld1.getNaechstes(), feld1.getNtesFeld(1));
		assertEquals(feld1.getNaechstes().getNaechstes(), feld1.getNtesFeld(2));
	}

	public void testIstFrei() {
		assertFalse(feld1.istFrei());
		assertTrue(feld2.istFrei());
	}

	public void testIstBesetzt() {
		assertTrue(feld1.istBesetzt());
		assertTrue(feld1.istBesetztVon(spieler1));
		assertFalse(feld2.istBesetzt());
		assertFalse(feld2.istBesetztVon(spieler1));
	}
	
	public void testVersetzeFigurAuf() {
		feld1.versetzeFigurAuf(feld2);
		assertTrue(feld1.istFrei());
		assertTrue(feld2.istBesetztVon(spieler1));
	}
	
	public void testGetHimmel() {
		assertEquals(brett.getHimmelFelderVon(spieler1).get(0),
		             bankFeld1.getHimmel());
	}
	
	public void testGetSpieler() {
		assertEquals(spieler1, lagerFeld1.getSpieler());
		assertEquals(spieler1, bankFeld1.getSpieler());
		assertEquals(spieler1, bankFeld1.getHimmel().getSpieler());
	}
}

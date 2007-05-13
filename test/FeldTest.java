import pd.brett.Feld;

public class FeldTest extends ProblemDomainTestCase {
	public void testNaechstesVorheriges() {
		Feld feld = bankFeld.getNaechstes().getVorheriges();
		assertEquals(bankFeld, feld);
	}

	public void testBesetzt() {
		assertTrue(bankFeld.istBesetzt());
		assertTrue(bankFeld.istBesetztVon(spieler));
		assertFalse(zielFeld.istBesetzt());
		assertFalse(zielFeld.istBesetztVon(spieler));
	}
}

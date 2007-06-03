package pd;
import pd.brett.Feld;

public class FeldTest extends ProblemDomainTestCase {
	private Feld feld1;
	private Feld feld2;
	
	protected void setUp() {
		super.setUp();
		feld1 = bank(0);
		feld2 = feld1.getNaechstes();
		lager(0).versetzeFigurAuf(feld1);
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
		assertTrue(feld1.istBesetztVon(spieler(0)));
		assertFalse(feld2.istBesetzt());
		assertFalse(feld2.istBesetztVon(spieler(0)));
	}
	
	public void testVersetzeFigurAuf() {
		feld1.versetzeFigurAuf(feld2);
		assertTrue(feld1.istFrei());
		assertTrue(feld2.istBesetztVon(spieler(0)));
	}
	
	public void testGetHimmel() {
		assertEquals(brett.getHimmelFelderVon(spieler(0)).get(0),
		             bank(0).getHimmel());
	}
	
	public void testGetSpieler() {
		assertEquals(spieler(0), lager(0).getSpieler());
		assertEquals(spieler(0), bank(0).getSpieler());
		assertEquals(spieler(0), bank(0).getHimmel().getSpieler());
	}
	
	public void testGetNummer() {
		assertEquals(0, bank(0).getNummer());
		assertEquals("BankFeld 0", bank(0).toString());
	}
}

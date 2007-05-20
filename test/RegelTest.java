import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Vier;
import pd.regelsystem.Regel;
import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.StartRegel;
import pd.regelsystem.VorwaertsRegel;
import pd.spieler.Figur;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class RegelTest extends ProblemDomainTestCase {
	private Karte vier;
	protected BankFeld bankFeld;
	protected Feld zielFeld;
	
	public void setUp() throws Exception {
		super.setUp();
		vier = new Vier(KartenFarbe.Herz, 0);
		bankFeld = brett.getBankFeldVon(spieler1);
		zielFeld = bankFeld.getNtesFeld(4);
	}
	
	public void testVier() throws RegelVerstoss {
		lagerFeld1.versetzeFigurAuf(bankFeld);
		Bewegung bewegung4 = new Bewegung(bankFeld, zielFeld);
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung4);
		
		Zug zug = ze.validiere();
		zug.ausfuehren();
		assertFalse(bewegung4.getStart().istBesetzt());
		assertTrue(bewegung4.getZiel().istBesetztVon(spieler1));
	}
	
	public void testVierFalsch() {
		lagerFeld1.versetzeFigurAuf(bankFeld);
		Bewegung bewegung3 = new Bewegung(bankFeld, zielFeld.getVorheriges());
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung3);
		try {
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertNotNull(rv);
		}
	}

	public void testVierRueckwaerts() throws RegelVerstoss {
		lagerFeld1.versetzeFigurAuf(zielFeld);
		Bewegung bewegung4 = new Bewegung(zielFeld, bankFeld);
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung4);
		
		Zug zug = ze.validiere();
		zug.ausfuehren();
		assertFalse(bewegung4.getStart().istBesetzt());
		assertTrue(bewegung4.getZiel().istBesetztVon(spieler1));
	}
	
	public void testVierRueckwaertsFalsch() {
		lagerFeld1.versetzeFigurAuf(zielFeld);
		Bewegung bewegung3 = new Bewegung(zielFeld, bankFeld.getVorheriges());
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung3);
		try {
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertNotNull(rv);
		}
	}
	
	public void testStartRegel() throws RegelVerstoss {
		Feld lagerFeld = brett.getLagerFelderVon(spieler1).get(0);
		
		Bewegung bewegung = new Bewegung(lagerFeld, bankFeld);
		ZugEingabe ze = new ZugEingabe(spieler1, null, bewegung);
		
		Regel regel = new StartRegel();
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
		assertFalse(lagerFeld.istBesetzt());
		assertTrue(bankFeld.istBesetztVon(spieler1));
	}
	
	public void testHeimSchicken() throws RegelVerstoss {
		zielFeld = bankFeld.getNtesFeld(5);
		lagerFeld1.versetzeFigurAuf(bankFeld);
		lagerFeld2.versetzeFigurAuf(zielFeld);
		Figur figur2 = zielFeld.getFigur();
		
		Bewegung bewegung = new Bewegung(bankFeld, zielFeld);
		ZugEingabe ze = new ZugEingabe(spieler1, null, bewegung);
		
		Regel regel = new VorwaertsRegel(5);
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
		assertFalse(bankFeld.istBesetzt());
		assertTrue(zielFeld.istBesetztVon(spieler1));
		assertEquals(figur2, lagerFeld2.getFigur());
	}
}

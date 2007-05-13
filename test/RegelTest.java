import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Vier;
import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class RegelTest extends ProblemDomainTestCase {
	private Karte vier;
	
	public void setUp() throws Exception {
		super.setUp();
		vier = new Vier(KartenFarbe.Herz, 0);
	}
	
	public void testVier() throws RegelVerstoss {
		Bewegung bewegung4 = new Bewegung(bankFeld, zielFeld);
		ZugEingabe ze = new ZugEingabe(spieler, vier, bewegung4);
		
		Zug zug = ze.validiere();
		zug.ausfuehren();
		assertNull(bewegung4.getStart().getFigur());
		assertNotNull(bewegung4.getZiel().getFigur());
	}
	
	public void testVierFalsch() {
		Bewegung bewegung3 = new Bewegung(bankFeld, zielFeld.getVorheriges());
		ZugEingabe ze = new ZugEingabe(spieler, vier, bewegung3);
		try {
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertNotNull(rv);
		}
	}

	public void testVierRueckwaerts() throws RegelVerstoss {
		bankFeld.versetzeFigurAuf(zielFeld);
		Bewegung bewegung4 = new Bewegung(zielFeld, bankFeld);
		ZugEingabe ze = new ZugEingabe(spieler, vier, bewegung4);
		
		Zug zug = ze.validiere();
		zug.ausfuehren();
		assertNull(bewegung4.getStart().getFigur());
		assertNotNull(bewegung4.getZiel().getFigur());
	}
	
	public void testVierRueckwaertsFalsch() {
		Bewegung bewegung3 = new Bewegung(zielFeld, bankFeld.getVorheriges());
		ZugEingabe ze = new ZugEingabe(spieler, vier, bewegung3);
		try {
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertNotNull(rv);
		}
	}
}

import pd.brett.Feld;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Vier;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;

public class RegelTest extends ProblemDomainTestCase {
	private Karte vier;
	
	public void setUp() throws Exception {
		super.setUp();
		vier = new Vier(KartenFarbe.HERZ, 0);
	}
	
	public void testVier() {
		Bewegung bewegung4 = new Bewegung(bankFeld, zielFeld);
		ZugEingabe ze = new ZugEingabe(spieler, vier, bewegung4);
		assertTrue(ze.validiere());
		
		Bewegung bewegung3 = new Bewegung(bankFeld, zielFeld.getVorheriges());
		ZugEingabe zeFalsch = new ZugEingabe(spieler, vier, bewegung3);
		assertFalse(zeFalsch.validiere());
		
		ze.ausfuehren();
		assertNull(bewegung4.getStart().getFigur());
		assertNotNull(bewegung4.getZiel().getFigur());
	}

	public void testVierRueckwaerts() {
		bankFeld.versetzeFigurAuf(zielFeld);
		
		Bewegung bewegung4 = new Bewegung(zielFeld, bankFeld);
		ZugEingabe ze = new ZugEingabe(spieler, vier, bewegung4);
		assertTrue(ze.validiere());
		
		Bewegung bewegung3 = new Bewegung(zielFeld, bankFeld.getVorheriges());
		ZugEingabe zeFalsch = new ZugEingabe(spieler, vier, bewegung3);
		assertFalse(zeFalsch.validiere());
		
		ze.ausfuehren();
		assertNull(bewegung4.getStart().getFigur());
		assertNotNull(bewegung4.getZiel().getFigur());
	}
	/* Verursacht(e) NullPointer Exception */
	public void testUngueltigerZug() {
		Feld a = bankFeld.getNtesFeld(3);
		Feld b = bankFeld.getNtesFeld(1);
		Bewegung bewegung = new Bewegung(a, b);
		ZugEingabe nonsenseZug = new ZugEingabe(spieler, vier, bewegung);
		
		assertFalse(nonsenseZug.validiere());
	}
}

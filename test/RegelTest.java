import pd.deck.Vier;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Feld;
import pd.zugsystem.Zug;

public class RegelTest extends ProblemDomainTestCase {
	public void testVier() {
		Bewegung bewegung4 = new Bewegung(bankFeld, zielFeld);
		Zug zug = new Zug(spieler, new Vier(), bewegung4);
		assertTrue(zug.validiere());
		
		Bewegung bewegung3 = new Bewegung(bankFeld, zielFeld.getVorheriges());
		Zug zugFalsch = new Zug(spieler, new Vier(), bewegung3);
		assertFalse(zugFalsch.validiere());
		
		zug.ausfuehren();
		assertNull(bewegung4.getStart().getFigur());
		assertNotNull(bewegung4.getZiel().getFigur());
	}

	public void testVierRueckwaerts() {
		bankFeld.versetzeFigurAuf(zielFeld);
		
		Bewegung bewegung4 = new Bewegung(zielFeld, bankFeld);
		Zug zug = new Zug(spieler, new Vier(), bewegung4);
		assertTrue(zug.validiere());
		
		Bewegung bewegung3 = new Bewegung(zielFeld, bankFeld.getVorheriges());
		Zug zugFalsch = new Zug(spieler, new Vier(), bewegung3);
		assertFalse(zugFalsch.validiere());
		
		zug.ausfuehren();
		assertNull(bewegung4.getStart().getFigur());
		assertNotNull(bewegung4.getZiel().getFigur());
	}
	/* Verursacht(e) NullPointer Exception */
	public void testUngueltigerZug() {
		Feld a = bankFeld.getNtesFeld(3);
		Feld b = bankFeld.getNtesFeld(1);
		Bewegung bewegung = new Bewegung(a, b);
		Zug nonsenseZug = new Zug(spieler, new Vier(), bewegung);
		
		assertFalse(nonsenseZug.validiere());
	}
}

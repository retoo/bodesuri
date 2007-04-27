import pd.deck.Vier;
import pd.regelsystem.Regel;
import pd.regelsystem.RueckwaertsRegel;
import pd.regelsystem.VorwaertsRegel;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;

public class RegelTest extends ProblemDomainTestCase {
	public void testVier() {
		Regel vierVorwaerts = new VorwaertsRegel(4);
		
		Bewegung bewegung4 = new Bewegung(bankFeld, zielFeld);
		Zug zug = new Zug(spieler, new Vier(), bewegung4);
		assertNotNull(vierVorwaerts.validiere(zug));
		
		Bewegung bewegung3 = new Bewegung(bankFeld, zielFeld.getVorheriges());
		Zug zugFalsch = new Zug(spieler, new Vier(), bewegung3);
		assertNull(vierVorwaerts.validiere(zugFalsch));
	}

	public void testVierRueckwaerts() {
		Regel vierRueckwaerts = new RueckwaertsRegel(4);
		
		zielFeld.setFigur(bankFeld.getFigur());
		bankFeld.setFigur(null);
		
		Bewegung bewegung4 = new Bewegung(zielFeld, bankFeld);
		Zug zug = new Zug(spieler, new Vier(), bewegung4);
		assertNotNull(vierRueckwaerts.validiere(zug));
		
		Bewegung bewegung3 = new Bewegung(zielFeld, bankFeld.getVorheriges());
		Zug zugFalsch = new Zug(spieler, new Vier(), bewegung3);
		assertNull(vierRueckwaerts.validiere(zugFalsch));
	}
}

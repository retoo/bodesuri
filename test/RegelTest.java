import PD.deck.Vier;
import PD.regelsystem.Regel;
import PD.regelsystem.RueckwaertsRegel;
import PD.regelsystem.VorwaertsRegel;
import PD.zugsystem.Bewegung;
import PD.zugsystem.Zug;

public class RegelTest extends ProblemDomainTestCase {
	public void testVier() {
		Regel vierVorwaerts = new VorwaertsRegel(4);
		
		Zug zug = new Zug(spieler, new Vier(), new Bewegung(bankFeld, zielFeld));
		assertTrue(vierVorwaerts.validiere(zug));
		
		Zug zugFalsch = new Zug(spieler, new Vier(),
		                        new Bewegung(bankFeld, zielFeld.getVorheriges()));
		assertFalse(vierVorwaerts.validiere(zugFalsch));
	}

	public void testVierRueckwaerts() {
		Regel vierRueckwaerts = new RueckwaertsRegel(4);
		
		zielFeld.setFigur(bankFeld.getFigur());
		bankFeld.setFigur(null);
		Zug zug = new Zug(spieler, new Vier(),
		                  new Bewegung(zielFeld, bankFeld));
		assertTrue(vierRueckwaerts.validiere(zug));
		
		Zug zugFalsch = new Zug(spieler, new Vier(),
		                        new Bewegung(zielFeld, bankFeld.getVorheriges()));
		assertFalse(vierRueckwaerts.validiere(zugFalsch));
	}
}

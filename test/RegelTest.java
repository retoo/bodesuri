import PD.Deck.Vier;
import PD.Regelsystem.Regel;
import PD.Regelsystem.RueckwaertsRegel;
import PD.Regelsystem.VorwaertsRegel;
import PD.Zugsystem.Bewegung;
import PD.Zugsystem.Zug;

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

import PD.Deck.Vier;
import PD.Regelsystem.Regel;
import PD.Regelsystem.VorwaertsRegel;
import PD.Zugsystem.Bewegung;
import PD.Zugsystem.Zug;

public class RegelTest extends ProblemDomainTestCase {
	public void testVier() {
		Zug zug = new Zug(spieler, new Vier(), new Bewegung(bankFeld, zielFeld));
		Regel viererVorwaerts = new VorwaertsRegel(4);
		assertTrue(viererVorwaerts.validiere(zug));
	}

	public void testVierFalsch() {
		Zug zug = new Zug(spieler, new Vier(),
		                  new Bewegung(bankFeld, zielFeld.getVorheriges()));
		Regel viererVorwaerts = new VorwaertsRegel(4);
		assertFalse(viererVorwaerts.validiere(zug));
	}
}

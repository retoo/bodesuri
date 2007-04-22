import PD.Deck.Karte;
import PD.Deck.Vier;
import PD.Regelsystem.Regel;
import PD.Zugsystem.Bewegung;
import PD.Zugsystem.Zug;

public class RegelTest extends ProblemDomainTest {
	public void testVier() {
		Bewegung bewegung = new Bewegung(bankFeld, zielFeld);
		Karte vier = new Vier();
		Zug zug = new Zug(spieler, vier, bewegung);
		for (Regel regel : vier.getRegeln()) {
			assertTrue(regel.validiere(zug));
		}
	}

	public void testVierFalsch() {
		Bewegung bewegung = new Bewegung(bankFeld, zielFeld.getVorheriges());
		Karte vier = new Vier();
		Zug zug = new Zug(spieler, vier, bewegung);
		for (Regel regel : vier.getRegeln()) {
			assertFalse(regel.validiere(zug));
		}
	}
}

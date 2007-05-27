import java.util.List;

import pd.karten.Deck;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Sechs;

public class KartenTest extends ProblemDomainTestCase {
	public void testKarte() {
		Karte sechs = new Sechs(KartenFarbe.Herz, 0);
		assertEquals(KartenFarbe.Herz, sechs.getKartenFarbe());
		assertEquals("Herz Sechs", sechs.toString());
	}
	
	public void testDeck() {
		List<Karte> deck = Deck.erstelleKarten(0);
		assertEquals(55, deck.size());
	}
	
	public void testGetKarte() {
		// Verhalten bei erneutem Mischeln
		for (int i = 0; i < 150; ++i) {
			assertNotNull(kartenGeber.getKarte());
		}
		
		// Es darf nicht 2 x hintereinander das selbe Objekt gezogen werden
		assertNotSame(kartenGeber.getKarte(), kartenGeber.getKarte());
	}
}

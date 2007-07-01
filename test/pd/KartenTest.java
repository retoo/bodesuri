package pd;
import java.util.List;

import pd.regelsystem.karten.Deck;
import pd.regelsystem.karten.Karte;
import pd.regelsystem.karten.KartenFarbe;
import pd.regelsystem.karten.Sechs;

/**
 * Teste die Funktionalitäten der Karte.
 */
public class KartenTest extends ProblemDomainTestCase {
	/**
	 * Prüft Getter-Methoden und Initialisierung der Karte.
	 */
	public void testKarte() {
		Karte sechs = new Sechs(KartenFarbe.Herz);
		assertEquals("Sechs", sechs.getName());
		assertEquals(KartenFarbe.Herz, sechs.getKartenFarbe());
		assertEquals("Herz Sechs", sechs.toString());
	}
	
	/**
	 * Prüft das Deck, ob es korrekt initialisiert wurde.
	 */
	public void testDeck() {
		List<Karte> deck = Deck.erstelleKarten();
		assertEquals(55, deck.size());
		// Für 100% Abdeckung
		new Deck();
	}
	
	/**
	 * Prüft Verhalten beim Mischens.
	 */
	public void testGetKarte() {
		// Verhalten bei erneutem Mischeln
		for (int i = 0; i < 150; ++i) {
			assertNotNull(kartenGeber.getKarte());
		}
		
		// Es darf nicht 2 x hintereinander das selbe Objekt gezogen werden
		assertNotSame(kartenGeber.getKarte(), kartenGeber.getKarte());
	}
}

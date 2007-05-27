import pd.Spiel;
import pd.spieler.Spieler;

public class SpielTest extends ProblemDomainTestCase {
	protected void setUp() {
		spiel = new Spiel();
	}
	
	public void testSpieler() {
		assertEquals(4, spiel.getSpieler().size());
		spiel.fuegeHinzu("Spieler 0");
		spiel.fuegeHinzu("Spieler 1");
		spiel.fuegeHinzu("Spieler 2");
		spiel.fuegeHinzu("Spieler 3");
		assertEquals(4, spiel.getSpieler().size());
		
		Spieler spieler0 = spiel.getSpieler().get(0);
		assertEquals(0, spieler0.getNummer());
		assertEquals("Spieler 0", spieler0.getName());
		assertEquals("Spieler Spieler 0", spieler0.toString());
	}
}

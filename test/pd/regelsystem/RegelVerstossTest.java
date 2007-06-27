package pd.regelsystem;

import pd.regelsystem.verstoesse.RegelVerstoss;
import junit.framework.TestCase;

/**
 * Testet die Funktionalität eines Regelverstosses.
 */
public class RegelVerstossTest extends TestCase {
	/**
	 * Erstellt einen neuen Regelverstoss und prüft, ob dieser korrekt
	 * erstellt und initialisiert wurde.
	 */
	public void testRegelVerstoss() {
		RegelVerstoss rv = new RegelVerstoss("Des Todes!");
		assertEquals("Regelverstoss: Des Todes!", rv.toString());
	}
}

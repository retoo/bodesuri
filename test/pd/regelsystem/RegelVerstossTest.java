package pd.regelsystem;

import junit.framework.TestCase;

public class RegelVerstossTest extends TestCase {
	public void testRegelVerstoss() {
		RegelVerstoss rv = new RegelVerstoss("Des Todes!");
		assertEquals("Regelverstoss: Des Todes!", rv.toString());
	}
}

package dienste.automat.testzustaende;

import dienste.automat.zustaende.Zustand;

/**
 * Repräsentiert einen TestZustand(-Alpha) im TestAutomaten, der zusätzlich
 * Übergänge zu anderen TestZuständen definiert hat.
 */
public class TestZustandAlpha extends TestZustand {
	/**
	 * Übergang nach TestZustandAlpha, also sich selbst.
	 */
	protected Class<? extends Zustand> a() {
		return TestZustandAlpha.class;
    }

	/**
	 * Übergang nach TestZustandBeta.
	 */
	protected Class<? extends Zustand> b() {
		return TestZustandBeta.class;
    }
}

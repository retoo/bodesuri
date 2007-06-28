package dienste.automat.testzustaende;

import dienste.automat.events.TestEventC;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

/**
 * Repräsentiert einen TestZustand(-Beta) im TestAutomaten, der zusätzlich
 * Übergänge zu anderen TestZuständen definiert hat.
 */
public class TestZustandBeta extends TestZustand {
	public Class<? extends Zustand> handle(Event e) {
		if (e instanceof TestEventC)
			c();

		return super.handle(e);
	}

	/**
	 * @return Übergang nach TestZustandBeta, also sich selbst.
	 */
	protected Class<? extends Zustand> a() {
		return TestZustandBeta.class;
    }

	/**
	 * @return Übergang nach TestZustandBeta, also sich selbst.
	 */
	protected Class<? extends Zustand> b() {
		return TestZustandBeta.class;
    }

	/**
	 * @return Kein übergang definiert.
	 */
	protected Class<? extends Zustand> c() {
		return keinUebergang();
	}
}

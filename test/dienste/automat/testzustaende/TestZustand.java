package dienste.automat.testzustaende;

import dienste.automat.events.TestEventA;
import dienste.automat.events.TestEventB;
import dienste.automat.events.TestExitEvent;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

/**
 * Repräsentiert einen TestZustand im TestAutomaten. Der TestZustand bietet
 * einige Hilfsmethoden die bei den Tests nützlich sind.
 */
public class TestZustand extends Zustand {
	public Class<? extends Zustand> handle(Event event) {

		if (event instanceof TestEventA)
			return a();
		else if (event instanceof TestEventB)
			return b();
		else if (event instanceof TestExitEvent) {
			return exitEvent((TestExitEvent) event);
		}

		return super.handle(event);
	}

	protected Class<? extends Zustand> exitEvent(TestExitEvent event) {
	    return EndZustand.class;
    }

	/**
	 * @return Default kein Übergang.
	 */
	protected Class<? extends Zustand> a() {
		return keinUebergang();
	}

	/**
	 * @return Default kein Übergang.
	 */
	protected Class<? extends Zustand> b() {
		return keinUebergang();
	}
}

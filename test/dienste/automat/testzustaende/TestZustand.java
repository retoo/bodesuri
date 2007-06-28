package dienste.automat.testzustaende;

import dienste.automat.Automat;
import dienste.automat.TestAutomat;
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
	protected TestAutomat automat;

	/**
	 * Weist dem angegebenen Automaten den Zustand zu.
	 * 
	 * @param automat Automat, dem der Zustand zugewiesen werden soll.
	 */
	public void setAutomat(Automat automat) {
		if (automat instanceof TestAutomat) {
			this.automat = (TestAutomat) automat;
		} else {
			throw new RuntimeException("Ups, spielDaten ist " + automat.getClass());
		}
	}

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

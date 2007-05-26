package dienste.automat.testzustaende;

import dienste.automat.AktiverZustand;
import dienste.automat.Automat;
import dienste.automat.EndZustand;
import dienste.automat.Event;
import dienste.automat.KeinUebergangException;
import dienste.automat.TestAutomat;
import dienste.automat.Zustand;
import dienste.automat.events.TestEventA;
import dienste.automat.events.TestEventB;
import dienste.automat.events.TestExitEvent;

public class AktiverTestZustand extends AktiverZustand {
	protected TestAutomat automat;

	@Override
	public void setAutomat(Automat automat) {
		if (automat instanceof TestAutomat) {
			this.automat = (TestAutomat) automat;
		} else {
			throw new RuntimeException("Ups, automat ist " + automat.getClass());
		}
	}

	public Zustand handle(Event event) {

		if (event instanceof TestEventA)
			return a();
		else if (event instanceof TestEventB)
			return b();
		else if (event instanceof TestExitEvent) {
			return exitEvent((TestExitEvent) event);
		}

		return super.handle(event);
	}

	Zustand exitEvent(TestExitEvent event) {
	    return automat.getZustand(EndZustand.class);
    }

	Zustand a() {
		return keinUebergang();
	}

	Zustand b() {
		return keinUebergang();
	}

	Zustand keinUebergang() {
		throw new KeinUebergangException("Kein Übergang definiert in Zustand "
		                                 + this);
	}
}

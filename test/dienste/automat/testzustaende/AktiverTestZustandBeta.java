package dienste.automat.testzustaende;

import dienste.automat.events.TestEventC;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

public class AktiverTestZustandBeta extends AktiverTestZustand {
	public Class<? extends Zustand> handle(Event e) {
		if (e instanceof TestEventC)
			c();

		return super.handle(e);
	}

	Class<? extends Zustand> a() {
		return AktiverTestZustandBeta.class;
    }

	Class<? extends Zustand> b() {
		return AktiverTestZustandBeta.class;
    }

	Class<? extends Zustand> c() {
		return keinUebergang();
	}
}

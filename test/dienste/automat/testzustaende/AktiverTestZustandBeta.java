package dienste.automat.testzustaende;

import dienste.automat.Event;
import dienste.automat.events.TestEventC;
import dienste.automat.zustaende.Zustand;

public class AktiverTestZustandBeta extends AktiverTestZustand {
	public Zustand handle(Event e) {
		if (e instanceof TestEventC) 
			c();
		
		return super.handle(e);
	}
	
	Zustand a() {
		return automat.getZustand(AktiverTestZustandBeta.class);
    }
	
	Zustand b() {
		return this;
    }
	
	Zustand c() {
		return keinUebergang();
	}
}

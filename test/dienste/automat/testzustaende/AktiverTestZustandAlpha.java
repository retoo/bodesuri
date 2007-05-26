package dienste.automat.testzustaende;

import dienste.automat.zustaende.Zustand;


public class AktiverTestZustandAlpha extends AktiverTestZustand {
	Zustand a() {
		return this;
    }
	
	Zustand b() {
		return automat.getZustand(AktiverTestZustandBeta.class);
    }
}

package dienste.automat.testzustaende;

import dienste.automat.zustaende.Zustand;


public class AktiverTestZustandAlpha extends AktiverTestZustand {
	Class<? extends Zustand> a() {
		return AktiverTestZustandAlpha.class;
    }

	Class<? extends Zustand> b() {
		return AktiverTestZustandBeta.class;
    }
}

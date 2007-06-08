package dienste.automat.testzustaende;

import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class PassiverTestZustandAlpha extends AktiverTestZustand implements
        PassiverZustand {
	public Class<? extends Zustand> handle() {
		return PassiverTestZustandBeta.class;
	}
}

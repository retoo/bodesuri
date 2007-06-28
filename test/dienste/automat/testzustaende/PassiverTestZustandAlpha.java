package dienste.automat.testzustaende;

import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Repräsentiert einen passiven Zustand für den Automaten, der einen
 * Übergang in den PassiverTestZustandBeta besitzt.
 */
public class PassiverTestZustandAlpha extends TestZustand implements
        PassiverZustand {
	public Class<? extends Zustand> handle() {
		return PassiverTestZustandBeta.class;
	}
}

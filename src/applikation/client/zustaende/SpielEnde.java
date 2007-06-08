package applikation.client.zustaende;

import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

public class SpielEnde extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		return EndZustand.class;
	}
}

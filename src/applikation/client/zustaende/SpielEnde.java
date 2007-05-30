package applikation.client.zustaende;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

public class SpielEnde extends PassiverClientZustand {
	public Zustand handle() {
		return automat.getZustand(EndZustand.class);
	}

}

package applikation.client.zustaende;

import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class StarteRunde extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		return KarteTauschenAuswaehlen.class;
	}
}

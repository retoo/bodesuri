package applikation.server.zustaende;

import applikation.nachrichten.RundenStart;
import dienste.automat.zustaende.Zustand;

public class StartRunde extends PassiverServerZustand {

	@Override
	public Zustand handle() {
		 automat.spielerschaft.starteRunde();

		/* TODO: Karten austeilen */

		automat.spielerschaft.broadcast(new RundenStart());

		return automat.getZustand(KartenTauschen.class);
	}
}

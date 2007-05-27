package applikation.server.zustaende;

import applikation.server.BodesuriServer;
import dienste.automat.Automat;
import dienste.automat.zustaende.PassiverZustand;

/**
 * Spezifischer passiver Server-Zustand
 */
public abstract class PassiveServerZustand extends PassiverZustand {
	protected BodesuriServer automat;

	@Override
	public void setAutomat(Automat automat) {
		this.automat = (BodesuriServer) automat;
	}
}

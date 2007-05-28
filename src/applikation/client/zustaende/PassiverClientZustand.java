package applikation.client.zustaende;

import applikation.client.BodesuriClient;
import dienste.automat.Automat;
import dienste.automat.zustaende.PassiverZustand;

/**
 * Spezifischer passiver Client-Zustand
 */
public abstract class PassiverClientZustand extends PassiverZustand {
	protected BodesuriClient automat;

	public void setAutomat(Automat automat) {
		this.automat = (BodesuriClient) automat;
	}
}

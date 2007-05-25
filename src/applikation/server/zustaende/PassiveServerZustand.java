package applikation.server.zustaende;

import applikation.server.BodesuriServer;
import dienste.automat.Automat;
import dienste.automat.PassiverZustand;

public abstract class PassiveServerZustand extends PassiverZustand {
	protected BodesuriServer machine;

	@Override
	public void setAutomat(Automat automat) {
		this.machine = (BodesuriServer) automat;
	}
}

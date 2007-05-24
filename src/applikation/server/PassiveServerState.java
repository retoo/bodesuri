package applikation.server;

import dienste.automat.Automat;
import dienste.automat.PassiverZustand;

public abstract class PassiveServerState extends PassiverZustand {
	protected BodesuriServer machine;

	@Override
	public void setAutomat(Automat automat) {
		this.machine = (BodesuriServer) automat;
	}
}

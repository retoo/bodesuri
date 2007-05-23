package applikation.client.zustaende;

import applikation.client.BodesuriClient;
import dienste.automat.Automat;
import dienste.automat.PassiverZustand;

public abstract class PassiverClientZustand extends PassiverZustand {
	protected BodesuriClient automat;

	@Override
    public void setAutomat(Automat automat) {
		this.automat = (BodesuriClient) automat;
    }
}

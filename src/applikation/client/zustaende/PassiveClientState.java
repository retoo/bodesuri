package applikation.client.zustaende;

import applikation.client.BodesuriClient;
import dienste.automat.Automat;
import dienste.automat.PassiveState;

public abstract class PassiveClientState extends PassiveState {
	protected BodesuriClient automat;

	@Override
    public void setMachine(Automat automat) {
		this.automat = (BodesuriClient) automat;
    }
}

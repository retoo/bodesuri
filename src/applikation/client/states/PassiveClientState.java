package applikation.client.states;

import applikation.client.BodesuriClient;
import dienste.statemachine.PassiveState;
import dienste.statemachine.Automat;

public abstract class PassiveClientState extends PassiveState {
	protected BodesuriClient automat;

	@Override
    public void setMachine(Automat automat) {
		this.automat = (BodesuriClient) automat;
    }
}

package applikation.client.states;

import applikation.client.BodesuriClient;
import dienste.statemachine.PassiveState;
import dienste.statemachine.Automat;

public abstract class PassiveClientState extends PassiveState {
	protected BodesuriClient machine;

	@Override
    public void setMachine(Automat machine) {
		this.machine = (BodesuriClient) machine;
    }
}

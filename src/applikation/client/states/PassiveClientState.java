package applikation.client.states;

import applikation.client.BodesuriClient;
import dienste.statemachine.PassiveState;
import dienste.statemachine.StateMachine;

public abstract class PassiveClientState extends PassiveState {
	protected BodesuriClient machine;

	@Override
    public void setMachine(StateMachine machine) {
		this.machine = (BodesuriClient) machine;
    }
}

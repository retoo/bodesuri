package applikation.client.states;

import ui.BodesuriView;
import dienste.statemachine.State;

public class SpielStart extends PassiveClientState {

	@Override
	protected State getNextState() {
		return machine.getState(Spiel.class);
	}

	protected void init() {
		machine.lobbyView.setVisible(false);

		machine.spielView = new BodesuriView(machine.spiel, machine.spielerIch);
		machine.spielView.setVisible(true);
	}

}

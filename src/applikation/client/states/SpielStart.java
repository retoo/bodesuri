package applikation.client.states;

import ui.BodesuriView;
import dienste.statemachine.State;

public class SpielStart extends PassiveClientState {

	@Override
	protected State getNextState() {
		return automat.getState(Spiel.class);
	}

	protected void init() {
		automat.lobbyView.setVisible(false);

		automat.spielView = new BodesuriView(automat.spiel, automat.spielerIch);
		automat.spielView.setVisible(true);
	}

}

package applikation.client.states;

import ui.lobby.LobbyView;
import dienste.statemachine.State;

public class LobbyStart extends PassiveClientState {

	@Override
	protected State getNextState() {
		return machine.getState(Lobby.class);
	}
	
	protected void init() {
		machine.verbindenView.setVisible(false);

		machine.lobbyView = new LobbyView();
		machine.lobbyView.setVisible(true);
	}

}

package applikation.client.zustaende;

import ui.lobby.LobbyView;
import dienste.automat.Zustand;

public class LobbyStart extends PassiverClientZustand {

	@Override
	protected Zustand getNextState() {
		return automat.getState(Lobby.class);
	}
	
	protected void init() {
		automat.verbindenView.setVisible(false);

		automat.lobbyView = new LobbyView();
		automat.lobbyView.setVisible(true);
	}

}

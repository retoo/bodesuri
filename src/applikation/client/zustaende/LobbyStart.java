package applikation.client.zustaende;

import ui.lobby.LobbyView;
import dienste.automat.Zustand;

public class LobbyStart extends PassiverClientZustand {

	@Override
	protected Zustand getNaechstenZustand() {
		return automat.getZustand(Lobby.class);
	}
	
	protected void init() {
		automat.verbindenView.setVisible(false);

		automat.lobbyView = new LobbyView();
		automat.lobbyView.setVisible(true);
	}

}

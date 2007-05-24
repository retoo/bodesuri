package applikation.client.zustaende;

import ui.lobby.LobbyView;
import dienste.automat.Zustand;

public class LobbyStart extends PassiverClientZustand {
	protected Zustand execute() {
		automat.verbindenView.setVisible(false);

		automat.lobbyView = new LobbyView();
		automat.lobbyView.setVisible(true);
		
		return automat.getZustand(Lobby.class);
	}
}
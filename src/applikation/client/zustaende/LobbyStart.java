package applikation.client.zustaende;

import ui.lobby.LobbyView;
import dienste.automat.zustaende.Zustand;

public class LobbyStart extends PassiverClientZustand {
	public Zustand handle() {
		automat.verbindenView.setVisible(false);

		automat.lobbyView = new LobbyView();
		automat.lobbyView.setVisible(true);
		
		return automat.getZustand(Lobby.class);
	}
}
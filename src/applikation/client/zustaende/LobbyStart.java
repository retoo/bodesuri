package applikation.client.zustaende;

import ui.lobby.LobbyView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem die Lobby ({@link LobbyView}) gestartet wird. Geht direkt in {@link Lobby}
 * über.
 */
public class LobbyStart extends PassiverClientZustand {
	public Zustand handle() {
		automat.verbindenView.setVisible(false);

		automat.lobbyView = new LobbyView();
		automat.lobbyView.setVisible(true);
		
		return automat.getZustand(Lobby.class);
	}
}
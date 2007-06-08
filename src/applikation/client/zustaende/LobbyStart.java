package applikation.client.zustaende;

import ui.lobby.LobbyView;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem die Lobby ({@link LobbyView}) gestartet wird. Geht direkt in {@link Lobby}
 * über.
 */
public class LobbyStart extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		controller.zeigeLobby();
		return Lobby.class;
	}
}
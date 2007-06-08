package applikation.client.zustaende;

import applikation.client.controller.Controller;
import ui.lobby.LobbyView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem die Lobby ({@link LobbyView}) gestartet wird. Geht direkt in {@link Lobby}
 * über.
 */
public class LobbyStart extends PassiverClientZustand {
	public LobbyStart(Controller controller) {
		this.controller = controller;
	}
	
	public Zustand handle() {		
		controller.zeigeLobby();
		return automat.getZustand(Lobby.class);
	}
}
package applikation.client.zustaende;

import applikation.client.ClientController;
import ui.lobby.LobbyView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem die Lobby ({@link LobbyView}) gestartet wird. Geht direkt in {@link Lobby}
 * über.
 */
public class LobbyStart extends PassiverClientZustand {
	public LobbyStart(ClientController controller) {
		/* TODO: 
		 * super(automat, controller);
		 */
		this.controller = controller;
	}
	
	public Zustand handle() {		
		controller.zeigeLobbyStart();
		return automat.getZustand(Lobby.class);
	}
}
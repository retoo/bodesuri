package applikation.client;

import pd.spieler.Spieler;
import ui.BodesuriView;
import ui.lobby.LobbyView;
import ui.verbinden.VerbindenView;
import applikation.client.states.Lobby;
import applikation.client.states.LobbyStart;
import applikation.client.states.ProgrammStart;
import applikation.client.states.SchwererFehlerState;
import applikation.client.states.Spiel;
import applikation.client.states.SpielStart;
import applikation.client.states.VerbindungErfassen;
import applikation.client.states.VerbindungWirdAufgebaut;
import dienste.netzwerk.EndPunkt;
import dienste.statemachine.EventQueue;
import dienste.statemachine.StateMachine;

public class BodesuriClient extends StateMachine {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public VerbindenView verbindenView;
	public LobbyView lobbyView;
	public BodesuriView spielView;

	public pd.Spiel spiel;
	public String spielerName;
	public Spieler spielerIch;

	public BodesuriClient(EventQueue queue) {
		register(new SchwererFehlerState());
		register(new ProgrammStart());
		register(new VerbindungErfassen());
		register(new VerbindungWirdAufgebaut());
		register(new LobbyStart());
		register(new Lobby());
		register(new SpielStart());
		register(new Spiel());

		setStartState(ProgrammStart.class);

		setEventSource(queue);

		this.queue = queue;
	}
}

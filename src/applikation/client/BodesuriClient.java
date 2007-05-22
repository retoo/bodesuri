package applikation.client;

import applikation.client.states.Lobby;
import applikation.client.states.ProgrammStart;
import applikation.client.states.SchwererFehlerState;
import applikation.client.states.VerbindungErfassen;
import dienste.netzwerk.EndPunkt;
import dienste.statemachine.EventQueue;
import dienste.statemachine.StateMachine;

public class BodesuriClient extends StateMachine {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public BodesuriClient(EventQueue queue) {
		register(new ProgrammStart());
		register(new VerbindungErfassen());
		register(new Lobby());
		register(new SchwererFehlerState());
		
		setStartState(ProgrammStart.class);

		setEventSource(queue);
		
		this.queue = queue;
	}
}

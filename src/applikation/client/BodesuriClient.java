package applikation.client;

import applikation.client.states.Lobby;
import applikation.client.states.ProgrammStart;
import dienste.netzwerk.EndPunkt;
import dienste.statemachine.EventQueue;
import dienste.statemachine.StateMachine;

public class BodesuriClient extends StateMachine {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public BodesuriClient(EventQueue queue) {
		register(new ProgrammStart());
		register(new Lobby());
		setStartState(ProgrammStart.class);

		setEventSource(queue);
		
		this.queue = queue;
	}
}

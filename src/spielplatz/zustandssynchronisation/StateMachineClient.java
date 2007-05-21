package spielplatz.zustandssynchronisation;

import spielplatz.zustandssynchronisation.states.Lobby;
import spielplatz.zustandssynchronisation.states.ProgrammStart;
import dienste.netzwerk.EndPunkt;

public class StateMachineClient extends StateMachine {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public StateMachineClient(EventQueue queue) {
		register(new ProgrammStart());
		register(new Lobby());
		setStartState(ProgrammStart.class);

		setEventSource(queue);
		
		this.queue = queue;
	}
}

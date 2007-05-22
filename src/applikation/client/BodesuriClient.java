package applikation.client;

import ui.verbinden.VerbindenView;
import applikation.client.states.VerbindungWirdAufgebaut;
import applikation.client.states.ProgrammStart;
import applikation.client.states.SchwererFehlerState;
import applikation.client.states.VerbindungErfassen;
import dienste.netzwerk.EndPunkt;
import dienste.statemachine.EventQueue;
import dienste.statemachine.StateMachine;

public class BodesuriClient extends StateMachine {
	public EventQueue queue;
	public EndPunkt endpunkt;
	public VerbindenView verbindenView;

	public BodesuriClient(EventQueue queue) {
		register(new ProgrammStart());
		register(new VerbindungErfassen());
		register(new VerbindungWirdAufgebaut());
		register(new SchwererFehlerState());
		
		setStartState(ProgrammStart.class);

		setEventSource(queue);
		
		this.queue = queue;
	}
}

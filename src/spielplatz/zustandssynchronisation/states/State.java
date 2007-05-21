package spielplatz.zustandssynchronisation.states;

import spielplatz.zustandssynchronisation.Event;
import spielplatz.zustandssynchronisation.StateMachine;
import spielplatz.zustandssynchronisation.UnbekannterEventException;

public class State {
	StateMachine machine;

	public State execute(Event event) {
		throw new UnbekannterEventException("State " + this + " didn't return the next state");
    }

	public void setMachine(StateMachine machine) {
		this.machine = machine;
    }
}

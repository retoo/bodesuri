package spielplatz.zustandssynchronisation.states;

import spielplatz.zustandssynchronisation.StateMachine;
import spielplatz.zustandssynchronisation.UnbekannterEventException;
import spielplatz.zustandssynchronisation.events.Event;

public abstract class State {
	public State execute(Event event) {
		throw new UnbekannterEventException("State " + this + " didn't return the next state");
    }

	abstract public void setMachine(StateMachine machine); 
}

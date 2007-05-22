package dienste.statemachine;


public abstract class State {
	public State execute(Event event) {
		throw new UnbekannterEventException("State " + this + " didn't return the next state");
    }

	abstract public void setMachine(StateMachine machine); 
}

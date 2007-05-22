package dienste.statemachine;

public abstract class ActiveState extends State {
	public State execute(Event event) {
		throw new UnbekannterEventException("State " + this + " didn't return the next state");
    }
}

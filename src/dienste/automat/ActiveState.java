package dienste.automat;

public abstract class ActiveState extends State {
	public State handle(Event event) {
		throw new UnbekannterEventException("State " + this + " didn't return the next state");
    }
}

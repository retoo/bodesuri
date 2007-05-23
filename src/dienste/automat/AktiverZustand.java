package dienste.automat;

public abstract class AktiverZustand extends Zustand {
	public Zustand handle(Event event) {
		throw new UnbekannterEventException("State " + this + " didn't return the next state");
    }
}

package dienste.automat;

public abstract class AktiverZustand extends Zustand {
	public Zustand handle(Event event) {
		throw new UnbekannterEventException("Zustand " + this + " hat keinen Übergang für Event.");
    }
}

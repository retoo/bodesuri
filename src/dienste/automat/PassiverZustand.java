package dienste.automat;

public abstract class PassiverZustand extends Zustand {
	abstract protected Zustand getNextState();
}

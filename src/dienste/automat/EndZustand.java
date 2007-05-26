package dienste.automat;


public class EndZustand extends PassiverZustand {

	@Override
	protected Zustand execute() {
		throw new RuntimeException("EndZustand sollte eigentlich nie ausgeführt werden!");
	}

	@Override
	public void setAutomat(Automat automat) {
	}
}

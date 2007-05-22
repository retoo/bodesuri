package dienste.statemachine;

public abstract class PassiveState extends State {
	public State execute() {
		init();
		
	    return getNextState();
    }

	abstract protected State getNextState();
}

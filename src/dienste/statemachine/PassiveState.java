package dienste.statemachine;

public abstract class PassiveState extends State {
	abstract protected State getNextState();
}

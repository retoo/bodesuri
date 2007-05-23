package dienste.automat;

public abstract class PassiveState extends State {
	abstract protected State getNextState();
}

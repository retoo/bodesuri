package dienste.statemachine;


public abstract class State {
	abstract public void setMachine(StateMachine machine);
	
	protected void init() {
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

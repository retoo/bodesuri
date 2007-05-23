package dienste.statemachine;


public abstract class State {
	abstract public void setMachine(Automat automat);
	
	protected void init() {
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

package dienste.automat;


public abstract class State {
	abstract public void setMachine(Automat automat);
	
	protected void init() {
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

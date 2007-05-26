package dienste.automat;


public abstract class Zustand {
	abstract public void setAutomat(Automat automat);
	
	protected void init() {
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
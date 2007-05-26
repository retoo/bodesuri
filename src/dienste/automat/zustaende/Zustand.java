package dienste.automat.zustaende;

import dienste.automat.Automat;


public abstract class Zustand {
	abstract public void setAutomat(Automat automat);
	
	public void init() {
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
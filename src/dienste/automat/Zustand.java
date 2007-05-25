package dienste.automat;


public abstract class Zustand {
	abstract public void setAutomat(Automat automat);
	
	protected void init() {
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}


abstract class CZustand {
	
}

class CPassiverZustand extends CZustand {
	
}

interface CAktiverZustand {
	
}
package dienste.automat.zustaende;

import dienste.automat.Automat;

/**
 * Finaler Zustand eines Automaten. Der Automat beendet sich wenn er in diesen
 * Zustand überführt wird.
 * 
 */
public class EndZustand extends Zustand {
	Automat automat;

	@Override
	public void setAutomat(Automat automat) {
		this.automat = automat;
	}
}

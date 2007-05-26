package dienste.automat.testzustaende;
import dienste.automat.Automat;
import dienste.automat.PassiverZustand;
import dienste.automat.TestAutomat;


public abstract class PassiverTestZustand extends PassiverZustand {
	protected TestAutomat automat;

	@Override
	public void setAutomat(Automat automat) {
		this.automat = (TestAutomat) automat;
	}
}

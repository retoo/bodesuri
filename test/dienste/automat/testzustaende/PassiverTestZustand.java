package dienste.automat.testzustaende;
import dienste.automat.Automat;
import dienste.automat.TestAutomat;
import dienste.automat.zustaende.PassiverZustand;


public abstract class PassiverTestZustand extends PassiverZustand {
	protected TestAutomat automat;

	@Override
	public void setAutomat(Automat automat) {
		this.automat = (TestAutomat) automat;
	}
}

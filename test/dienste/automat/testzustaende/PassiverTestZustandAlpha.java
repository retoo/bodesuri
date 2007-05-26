package dienste.automat.testzustaende;
import dienste.automat.Zustand;


public class PassiverTestZustandAlpha extends PassiverTestZustand {

	@Override
	protected Zustand execute() {
		return automat.getZustand(PassiverTestZustandBeta.class);
	}

}

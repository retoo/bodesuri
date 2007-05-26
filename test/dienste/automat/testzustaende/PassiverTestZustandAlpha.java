package dienste.automat.testzustaende;
import dienste.automat.zustaende.Zustand;


public class PassiverTestZustandAlpha extends PassiverTestZustand {

	@Override
    public Zustand handle() {
		return automat.getZustand(PassiverTestZustandBeta.class);
	}

}

package dienste.automat.testzustaende;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;


public class PassiverTestZustandBeta extends PassiverTestZustand {
	@Override
    public Zustand handle() {
		return automat.getZustand(EndZustand.class);
	}
}

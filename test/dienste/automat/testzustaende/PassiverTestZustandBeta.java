package dienste.automat.testzustaende;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;


public class PassiverTestZustandBeta extends AktiverTestZustand implements PassiverZustand {
    public Class<? extends Zustand> handle() {
		return EndZustand.class;
	}
}

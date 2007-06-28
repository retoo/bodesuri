package dienste.automat.testzustaende;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Repräsentiert einen passiven Zustand für den Automaten, der einen
 * Übergang in den EndZustand besitzt.
 */
public class PassiverTestZustandBeta extends TestZustand implements PassiverZustand {
    public Class<? extends Zustand> handle() {
		return EndZustand.class;
	}
}

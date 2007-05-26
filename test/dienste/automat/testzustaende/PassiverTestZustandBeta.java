package dienste.automat.testzustaende;
import dienste.automat.EndZustand;
import dienste.automat.Zustand;


public class PassiverTestZustandBeta extends PassiverTestZustand {
	@Override
	protected Zustand execute() {
		return automat.getZustand(EndZustand.class);
	}
}

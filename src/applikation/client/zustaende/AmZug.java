package applikation.client.zustaende;

import applikation.client.zugautomat.ZugAutomat;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler dran kommt. Bis auf weiteres ein passiver
 * Zustand, da hier nichts getan werden muss. Geht direkt in {@link NichtAmZug} über.
 */
public class AmZug extends PassiverClientZustand {
	public Zustand handle() {
		automat.zugAutomat = new ZugAutomat(automat.spielerIch, automat.endpunkt);
		
		automat.zugAutomat.run();
		
		return automat.getZustand(NichtAmZug.class);
	}

}

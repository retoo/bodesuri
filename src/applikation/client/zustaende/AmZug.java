package applikation.client.zustaende;

import applikation.client.zugautomat.ZugAutomat;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler dran kommt. Erstellt einen ZugAutomat der sich
 * um das erfassen, versenden & validieren eines Zuges kümmert. Geht danach in
 * {@link NichtAmZug} über.
 */
public class AmZug extends PassiverClientZustand {
	public Zustand handle() {
		automat.zugAutomat = new ZugAutomat(automat.spielerIch,
		                                    automat.endpunkt);

		automat.zugAutomat.run();

		return automat.getZustand(NichtAmZug.class);
	}

}

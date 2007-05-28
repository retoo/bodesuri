package applikation.client.zustaende;

import ui.spiel.BodesuriView;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem das Spiel ({@link BodesuriView}) gestartet wird. Geht
 * direkt in {@link NichtAmZug} über.
 */
public class SpielStart extends PassiverClientZustand {
	public Zustand handle() {

		automat.lobbyView.setVisible(false);

		automat.spielView = new BodesuriView(automat);
		automat.spielView.setVisible(true);
		return automat.getZustand(NichtAmZug.class);
	}
}

package applikation.client.zustaende;

import ui.spiel.BodesuriView;
import dienste.automat.zustaende.Zustand;

public class SpielStart extends PassiverClientZustand {

	@Override
    public Zustand handle() {

		automat.lobbyView.setVisible(false);

		automat.spielView = new BodesuriView(automat);
		automat.spielView.setVisible(true);
		return automat.getZustand(NichtAmZug.class);
	}
}

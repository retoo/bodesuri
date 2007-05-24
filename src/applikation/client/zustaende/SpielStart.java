package applikation.client.zustaende;

import ui.BodesuriView;
import dienste.automat.Zustand;

public class SpielStart extends PassiverClientZustand {

	@Override
	protected Zustand execute() {

		automat.lobbyView.setVisible(false);

		automat.spielView = new BodesuriView(automat);
		automat.spielView.setVisible(true);
		return automat.getZustand(NichtAmZug.class);
	}
}

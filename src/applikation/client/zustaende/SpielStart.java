package applikation.client.zustaende;

import ui.BodesuriView;
import dienste.automat.Zustand;

public class SpielStart extends PassiverClientZustand {

	@Override
	protected Zustand getNaechstenZustand() {
		return automat.getZustand(Spiel.class);
	}

	protected void init() {
		automat.lobbyView.setVisible(false);

		automat.spielView = new BodesuriView(automat.spiel, automat.spielerIch);
		automat.spielView.setVisible(true);
	}

}

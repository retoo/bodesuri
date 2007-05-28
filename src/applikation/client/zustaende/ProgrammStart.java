package applikation.client.zustaende;

import ui.verbinden.VerbindenView;
import dienste.automat.zustaende.Zustand;

public class ProgrammStart extends PassiverClientZustand {
    public Zustand handle() {
		automat.verbindenView = new VerbindenView(automat.queue);
		automat.verbindenView.setVisible(true);
	    return automat.getZustand(VerbindungErfassen.class);
    }
}

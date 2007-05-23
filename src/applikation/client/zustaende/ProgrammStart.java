package applikation.client.zustaende;

import ui.verbinden.VerbindenView;
import dienste.automat.Zustand;

public class ProgrammStart extends PassiverClientZustand {
	@Override
    protected Zustand getNextState() {
	    return automat.getState(VerbindungErfassen.class);
    }
	
	protected void init() {
		automat.verbindenView = new VerbindenView(automat.queue);
		automat.verbindenView.setVisible(true);
	}	
}

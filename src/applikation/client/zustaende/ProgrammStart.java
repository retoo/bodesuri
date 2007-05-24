package applikation.client.zustaende;

import ui.verbinden.VerbindenView;
import dienste.automat.Zustand;

public class ProgrammStart extends PassiverClientZustand {
	@Override
    protected Zustand execute() {
	    return automat.getZustand(VerbindungErfassen.class);
    }
	
	protected void init() {
		automat.verbindenView = new VerbindenView(automat.queue);
		automat.verbindenView.setVisible(true);
	}	
}

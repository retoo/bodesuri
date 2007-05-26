package applikation.client.zustaende;

import ui.verbinden.VerbindenView;
import dienste.automat.zustaende.Zustand;

public class ProgrammStart extends PassiverClientZustand {
	@Override
    public Zustand handle() {
	    return automat.getZustand(VerbindungErfassen.class);
    }
	
	public void init() {
		automat.verbindenView = new VerbindenView(automat.queue);
		automat.verbindenView.setVisible(true);
	}	
}

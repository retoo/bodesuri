package applikation.client.states;

import ui.verbinden.VerbindenView;
import dienste.statemachine.State;

public class ProgrammStart extends PassiveClientState {
	@Override
    protected State getNextState() {
	    return automat.getState(VerbindungErfassen.class);
    }
	
	protected void init() {
		automat.verbindenView = new VerbindenView(automat.queue);
		automat.verbindenView.setVisible(true);
	}	
}

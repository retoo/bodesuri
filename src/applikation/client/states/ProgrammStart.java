package applikation.client.states;

import ui.verbinden.VerbindenView;
import dienste.statemachine.State;

public class ProgrammStart extends PassiveClientState {
	@Override
    protected State getNextState() {
	    return machine.getState(VerbindungErfassen.class);
    }
	
	protected void init() {
		machine.verbindenView = new VerbindenView(machine.queue);
		machine.verbindenView.setVisible(true);
	}	
}

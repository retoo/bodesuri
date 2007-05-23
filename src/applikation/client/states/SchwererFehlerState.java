package applikation.client.states;

import dienste.automat.State;

public class SchwererFehlerState extends PassiveClientState {
	public void init() {
		System.out.println("Error");
	}
	
	@Override
    protected State getNextState() {
		throw new RuntimeException("Schwerer Fehler");
    }
}

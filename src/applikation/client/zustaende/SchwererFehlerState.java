package applikation.client.zustaende;

import dienste.automat.Zustand;

public class SchwererFehlerState extends PassiverClientZustand {
	public void init() {
		System.out.println("Error");
	}
	
	@Override
    protected Zustand getNextState() {
		throw new RuntimeException("Schwerer Fehler");
    }
}

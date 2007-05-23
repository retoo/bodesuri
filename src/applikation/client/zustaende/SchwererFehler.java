package applikation.client.zustaende;

import dienste.automat.Zustand;

public class SchwererFehler extends PassiverClientZustand {
	public void init() {
		System.out.println("Error");
	}
	
	@Override
    protected Zustand getNaechstenZustand() {
		throw new RuntimeException("Schwerer Fehler");
    }
}

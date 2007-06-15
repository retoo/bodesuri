package applikation.client.pd;

import java.util.List;
import java.util.Vector;

public class Spiel {
	private pd.Spiel spiel;
	
	private Brett brett;
	private Vector<Spieler> spieler;
	
	public Spiel() {
		spiel = new pd.Spiel();
		
		spieler = new Vector<Spieler>();
		brett = new Brett(spiel.getBrett());
	}
	
	//TODO: Heisst bei Reto neuerSpieler()
	public Spieler fuegeHinzu(String spielerName) {
		Spieler neuerSpieler = new Spieler(spiel.fuegeHinzu(spielerName));
		spieler.add(neuerSpieler);
		return neuerSpieler;
	}

	public Brett getBrett() {
	    return brett;
    }
	
	public List<Spieler> getSpieler() {
    	return spieler;
    }

	public pd.Spiel getSpiel() {
	    return spiel;
    }
}

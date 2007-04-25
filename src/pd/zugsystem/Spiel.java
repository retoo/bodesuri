package pd.zugsystem;

import java.util.Vector;

import pd.spielerverwaltung.Spieler;

public class Spiel {
	private Brett brett;
	
	private Vector<Spieler> spieler = new Vector<Spieler>();
	
	public void fuegeHinzu(Spieler s) {
		spieler.add(s);
	}
	
	/* TODO: Methodenname anpassen und Code sowieso ;) */
	public void brettAufstellen() {
		if (spieler.size() != 4) {
			throw new Error("Noch nicht genug Spieler im Spiel.");
		}
		
		brett = new Brett(this);
	}

	public Brett getBrett() {
    	return brett;
    }

	public Vector<Spieler> getSpieler() {
    	return spieler;
    }
}

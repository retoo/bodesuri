package pd.zugsystem;

import java.util.Vector;

import pd.spielerverwaltung.Spieler;

public class Spiel {
	private static final int ANZAHL_SPIELER = 4;
	
	private Brett brett;
	
	private Vector<Spieler> spieler = new Vector<Spieler>();
	
	public void fuegeHinzu(Spieler s) {
		spieler.add(s);
	}
	
	/* TODO: Methodenname anpassen und Code sowieso ;) */
	/* sollte man das nicht im Konstruktur machen ? (-rschuett) */
	public void brettAufstellen() {
		if (spieler.size() != ANZAHL_SPIELER) {
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

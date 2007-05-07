package pd.zugsystem;

import java.util.Vector;

import pd.spielerverwaltung.Spieler;

public class Spiel {
	private static final int ANZAHL_SPIELER = 4;
	
	private Brett brett;
	private Vector<Spieler> spieler = new Vector<Spieler>();
	
	private int beigetreteneSpieler = 0;
	
	public Spiel() {
		for (int i = 0; i < ANZAHL_SPIELER; ++i) {
			spieler.add(new Spieler());
		}
		brett = new Brett(this);
	}
	
	public void fuegeHinzu(String spielerName) {
		spieler.get(beigetreteneSpieler).setName(spielerName);
		++beigetreteneSpieler;
	}

	public Brett getBrett() {
    	return brett;
    }

	public Vector<Spieler> getSpieler() {
    	return spieler;
    }
}

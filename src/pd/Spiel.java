package pd;

import java.util.Vector;

import dienste.serialisierung.Codierer;

import pd.brett.Brett;
import pd.deck.KartenGeber;
import pd.spieler.Spieler;

public class Spiel {
	private static final int ANZAHL_SPIELER = 4;
	
	private Brett brett;
	private Vector<Spieler> spieler = new Vector<Spieler>();
	private Codierer codierer = new Codierer();
	private int beigetreteneSpieler = 0;
	private KartenGeber kartenGeber;
	
	/* SCHEISSE */
	public static Spiel aktuelles;
	
	public Spiel() {
		/* SCHEISSE */
		Spiel.aktuelles = this;
		kartenGeber = new KartenGeber();
		for (int i = 0; i < ANZAHL_SPIELER; ++i) {
			Spieler sp = new Spieler(i);
			spieler.add(sp);
			codierer.speichere(sp);
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

	public Codierer getCodierer() {
		return codierer;
	}

	public Vector<Spieler> getSpieler() {
    	return spieler;
    }

	public KartenGeber getKartenGeber() {
    	return kartenGeber;
    }
}

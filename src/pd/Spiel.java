package pd;

import java.util.List;
import java.util.Vector;

import pd.brett.Brett;
import pd.karten.KartenGeber;
import pd.spieler.Spieler;
import dienste.serialisierung.Codierer;

/**
 * Spiel, welches ein Brett, einen Kartengeber und 4 Spieler umfasst.
 */
public class Spiel {
	private static final int ANZAHL_SPIELER = 4;
	
	private Brett brett;
	private Vector<Spieler> spieler = new Vector<Spieler>();
	private Codierer codierer = new Codierer();
	private int beigetreteneSpieler = 0;
	private KartenGeber kartenGeber;
	
	/**
	 * Aktuelles Spiel. Wird für {@link Codierer} benötigt.
	 */
	public static Spiel aktuelles;
	
	/**
	 * Erstellt ein Spiel. Das Brett wird erstellt und die Spieler. Später
	 * werden den Spielern noch Namen gegeben, dann kann das Spiel beginnen!
	 */
	public Spiel() {
		Spiel.aktuelles = this;
		kartenGeber = new KartenGeber();
		for (int i = 0; i < ANZAHL_SPIELER; ++i) {
			Spieler sp = new Spieler(i, this);
			spieler.add(sp);
		}
		brett = new Brett(this);
	}
	
	/**
	 * Gebe dem nächsten Spieler einen Namen. 
	 * 
	 * @param spielerName Spielername
	 */
	public void fuegeHinzu(String spielerName) {
		spieler.get(beigetreteneSpieler).setName(spielerName);
		++beigetreteneSpieler;
	}

	/**
	 * @return Brett, auf dem gespielt wird
	 */
	public Brett getBrett() {
    	return brett;
    }

	/**
	 * @return Codierer, der die Objekte dieses Spiels kodiert
	 */
	public Codierer getCodierer() {
		return codierer;
	}

	/**
	 * @return Liste von Spielern, die mitspielen
	 */
	public List<Spieler> getSpieler() {
    	return spieler;
    }

	/**
	 * @return Kartengeber dieses Spiels, von dem Karten bezogen werden können
	 */
	public KartenGeber getKartenGeber() {
    	return kartenGeber;
    }
}

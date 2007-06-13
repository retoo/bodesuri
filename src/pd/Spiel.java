package pd;

import java.util.List;
import java.util.Vector;

import pd.brett.Brett;
import pd.karten.KartenGeber;
import pd.spieler.Spieler;
import pd.spieler.SpielerFarbe;
import dienste.serialisierung.Codierer;
import dienste.serialisierung.CodiererInterface;

/**
 * Spiel, welches ein Brett, einen Kartengeber und 4 Spieler umfasst.
 */
public class Spiel {
	private static final int ANZAHL_SPIELER = 4;

	private Brett brett;
	private Vector<Spieler> spieler;
	private CodiererInterface codierer;
	private int beigetreteneSpieler = 0;
	private KartenGeber kartenGeber;

	/**
	 * Erstellt ein Spiel. Das Brett wird erstellt und die Spieler. Später
	 * werden den Spielern noch Namen gegeben, dann kann das Spiel beginnen!
	 */
	public Spiel() {
		codierer = new Codierer();
		SpielThreads.registriere(Thread.currentThread(), this);

		spieler = new Vector<Spieler>();
		for (int i = 0; i < ANZAHL_SPIELER; ++i) {
			Spieler sp = new Spieler(i, this, SpielerFarbe.values()[i]);
			spieler.add(sp);
		}

		kartenGeber = new KartenGeber();

		brett = new Brett(this);
	}

	/**
	 * Gebe dem nächsten Spieler einen Namen.
	 *
	 * @param spielerName
	 *            Spielername
	 * @return Der neu erstellte Spieler.
	 */
	public Spieler fuegeHinzu(String spielerName) {
		Spieler neuerSpieler = spieler.get(beigetreteneSpieler);
		neuerSpieler.setName(spielerName);
		++beigetreteneSpieler;
		return neuerSpieler;
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
	public CodiererInterface getCodierer() {
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

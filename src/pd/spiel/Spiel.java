package pd.spiel;

import java.util.List;
import java.util.Vector;

import pd.spiel.brett.Brett;
import pd.spiel.spieler.Spieler;
import pd.spiel.spieler.SpielerFarbe;

/**
 * Spiel, welches ein Brett und 4 Spieler umfasst.
 */
public class Spiel {
	private static final int ANZAHL_SPIELER = 4;

	private Brett brett;
	private Vector<Spieler> spieler;
	private int beigetreteneSpieler = 0;

	/**
	 * Erstellt ein Spiel. Das Brett wird erstellt und die Spieler. Später
	 * werden den Spielern noch Namen gegeben, dann kann das Spiel beginnen!
	 */
	public Spiel() {
		spieler = new Vector<Spieler>();
		for (int i = 0; i < ANZAHL_SPIELER; ++i) {
			Spieler sp = new Spieler(i, this, SpielerFarbe.values()[i]);
			spieler.add(sp);
		}

		brett = new Brett(spieler);
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
	 * @return Liste von Spielern, die mitspielen
	 */
	public List<Spieler> getSpieler() {
    	return spieler;
    }
}

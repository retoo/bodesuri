package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die dem Spieler den Beitritt zum Spiel bestätigt.
 */
public class BeitrittsBestaetigung extends Nachricht {
	/**
	 * Name der bereits
	 */
	public final String[] spielerNamen;

	/**
	 * @param spielerNamen
	 */
	public BeitrittsBestaetigung(String[] spielerNamen) {
		this.spielerNamen = spielerNamen;
	}
}

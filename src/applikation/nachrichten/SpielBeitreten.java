package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die dem Server mitteilt, dass ein Spieler gerne teilnehmen würde.
 */
public class SpielBeitreten extends Nachricht {
	public static final String SpielerName = null;
	/**
	 * Name des zu beitretetenden Spielers.
	 */
	final public String spielerName;

	/**
	 * @param spielerName
	 */
	public SpielBeitreten(String spielerName) {
		this.spielerName = spielerName;
	}
}

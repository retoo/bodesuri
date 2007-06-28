package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Client dem Server mitteilt dass er gerne bei dessen Spiel
 * dabei sein möchte.
 */
public class SpielBeitreten extends Nachricht {
	final public String spielerName;

	public SpielBeitreten(String spielerName) {
		this.spielerName = spielerName;
	}
}

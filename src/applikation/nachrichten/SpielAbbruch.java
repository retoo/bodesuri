package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Server dem Client mitteilt, dass das Spiel abgebrochen
 * werden soll.
 */
public class SpielAbbruch extends Nachricht {
	public final String nachricht;

	public SpielAbbruch(String nachricht) {
		this.nachricht = nachricht;
	}
}

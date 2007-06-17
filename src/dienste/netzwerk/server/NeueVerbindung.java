package dienste.netzwerk.server;

import dienste.eventqueue.Event;
import dienste.netzwerk.Empfaenger;
import dienste.netzwerk.EndPunktInterface;

/**
 * Meldet eine neue Verbindung. Wird durch den {@link Empfaenger} bei
 * Verbindungsaufbau erstellt. Da nur der Server neue Verbindungen zulässt ist
 * dieser Event nur für den Server relevant.
 */
public class NeueVerbindung extends Event {
	/**
	 * EndPunkt der frisch erstellten Verbindung
	 */
	public final EndPunktInterface endpunkt;

	/**
	 * Erstellt Nachricht
	 * * @param client
	 */
	public NeueVerbindung(EndPunktInterface client) {
		this.endpunkt = client;
	}
}

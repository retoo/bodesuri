package dienste.netzwerk;

import dienste.automat.Event;

/**
 * Meldet eine neue Verbindung. Wird durch den {@link Empfaenger} bei
 * Verbindungsaufbau erstellt. Da nur der Server neue Verbindungen zulässt ist
 * dieser Event nur für den Server relevant.		
 */
public class NeueVerbindung extends Event {
	/**
	 * EndPunkt der frisch erstellten Verbindung
	 */
	public final EndPunkt endpunkt;

	public NeueVerbindung(EndPunkt client) {
		this.endpunkt = client;
	}
}

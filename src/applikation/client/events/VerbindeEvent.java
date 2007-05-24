package applikation.client.events;

import dienste.automat.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer eine Verbindung zum Server herstellen will.
 */
public class VerbindeEvent extends Event {
	public final String hostname;
	public final Integer port;
	public final String spielerName;

	/**
	 * Der Benutzer will eine Verbindung zum Server herstellen.
	 * @param hostname Der Hostname des Servers
	 * @param port Der Port des Servers
	 * @param spielerName Name das Spielers
	 */
	public VerbindeEvent(String hostname, Integer port, String spielerName) {
		this.hostname = hostname;
		this.port = port;
		this.spielerName = spielerName;
	}
}

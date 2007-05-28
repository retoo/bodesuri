package applikation.events;

import dienste.automat.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer eine Verbindung zum Server herstellen will.
 */
public class VerbindeEvent extends Event {
	/**
	 * Der Hostname des Servers
	 */
	public final String hostname;
	/**
	 * Der Port des Servers
	 */
	public final Integer port;
	/**
	 * Der Name des Spielers
	 */
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

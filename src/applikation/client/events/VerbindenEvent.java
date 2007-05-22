package applikation.client.events;

import dienste.statemachine.Event;

public class VerbindenEvent extends Event {
	public final String hostname;
	public final Integer port;
	public final String spieler;

	public VerbindenEvent(String hostname, Integer port, String spieler) {
		this.hostname = hostname;
		this.port = port;
		this.spieler = spieler;
	}
}

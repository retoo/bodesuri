package applikation.client.events;

import dienste.automat.Event;

public class VerbindeEvent extends Event {
	public final String hostname;
	public final Integer port;
	public final String spielerName;

	public VerbindeEvent(String hostname, Integer port, String spieler) {
		this.hostname = hostname;
		this.port = port;
		this.spielerName = spieler;
	}
}

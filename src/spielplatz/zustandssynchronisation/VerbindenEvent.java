package spielplatz.zustandssynchronisation;

import dienste.netzwerk.Nachricht;

public class VerbindenEvent extends Nachricht {
	private static final long serialVersionUID = 1L;
	
	public final String hostname;
	public final Integer port;
	public final String spieler;

	public VerbindenEvent(String hostname, Integer port, String spieler) {
		this.hostname = hostname;
		this.port = port;
		this.spieler = spieler;
    }
}

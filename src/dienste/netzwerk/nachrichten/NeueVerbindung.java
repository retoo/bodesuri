package dienste.netzwerk.nachrichten;

import dienste.netzwerk.EndPunkt;
import spielplatz.hilfsklassen.ServerMeldung;

public class NeueVerbindung extends ServerMeldung {
	private static final long serialVersionUID = 1L;

	public EndPunkt client;

	public NeueVerbindung(EndPunkt client) {
		this.client = client;
	}

}

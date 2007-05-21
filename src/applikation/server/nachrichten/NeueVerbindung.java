package applikation.server.nachrichten;

import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;

public class NeueVerbindung extends Nachricht {
	private static final long serialVersionUID = 1L;

	final public EndPunkt client;

	public NeueVerbindung(EndPunkt client) {
		this.client = client;
	}

}

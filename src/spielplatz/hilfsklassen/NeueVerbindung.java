package spielplatz.hilfsklassen;

import spielplatz.EndPunkt;

public class NeueVerbindung extends ServerMeldung {
	private static final long serialVersionUID = 1L;

	public EndPunkt client;

	public NeueVerbindung(EndPunkt client) {
		this.client = client;
	}

}

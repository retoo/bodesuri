package applikation.server.nachrichten;


import dienste.automat.Event;
import dienste.netzwerk.EndPunkt;

public class NeueVerbindung extends Event {
	public final EndPunkt endpunkt;

	public NeueVerbindung(EndPunkt client) {
	    this.endpunkt = client;
    }
}

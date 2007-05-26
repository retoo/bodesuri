package dienste.netzwerk;


import dienste.automat.Event;

public class NeueVerbindung extends Event {
	public final EndPunkt endpunkt;

	public NeueVerbindung(EndPunkt client) {
	    this.endpunkt = client;
    }
}

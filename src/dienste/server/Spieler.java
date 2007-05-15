package dienste.server;

import dienste.netzwerk.EndPunkt;

public class Spieler {

	public String name;
	public EndPunkt endpunkt;

	public Spieler(EndPunkt endpunkt, String name) {
	    this.name = name;
	    this.endpunkt = endpunkt;
    }
	
	public String toString() {
		return name + " (" + endpunkt + ")";
	}
}

package applikation.server;

import dienste.netzwerk.EndPunkt;

/**
 * Repräsentiert den Spieler auf dem Server.
 */
public class Spieler {

	public String spielerName;
	public EndPunkt endpunkt;

	public Spieler(EndPunkt endpunkt, String name) {
	    this.spielerName = name;
	    this.endpunkt = endpunkt;
    }
	
	public String toString() {
		return spielerName + " (" + endpunkt + ")";
	}
}

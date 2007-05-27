package applikation.server;

import dienste.netzwerk.EndPunkt;

/**
 * Repräsentiert den Spieler auf dem Server.
 */
public class Spieler {
	/**
	 * Name des Spielers
	 */
	public String name;

	/**
	 * Endpunkt des Spielers
	 * 
	 * FIXME: das sollte früher doer später private sein
	 */
	public EndPunkt endpunkt;

	/**
	 * Erstellt einen neune Spieler
	 * 
	 * @param endpunkt
	 *            Endpunkt des Spielers
	 * @param name
	 *            Name des Spielers
	 */
	public Spieler(EndPunkt endpunkt, String name) {
		this.name = name;
		this.endpunkt = endpunkt;
	}

	public String toString() {
		return name + " (" + endpunkt + ")";
	}
}

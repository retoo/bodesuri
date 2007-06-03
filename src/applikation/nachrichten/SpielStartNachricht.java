package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die allen Spielern meldet dass das Spiel nun beginnt.
 */
public class SpielStartNachricht extends Nachricht {
	/**
	 * Namen aller teilnehmenden Spieler
	 */
	final public String[] spieler;

	public SpielStartNachricht(String[] spieler) {
		this.spieler = spieler;
	}
}

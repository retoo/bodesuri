package applikation.nachrichten;

import applikation.server.pd.SpielInfo;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht die allen Spielern meldet dass das Spiel nun beginnt.
 */
public class SpielStartNachricht extends Nachricht {
	/**
	 * Namen aller teilnehmenden Spieler
	 */
	public final SpielInfo spielInfo;

	public SpielStartNachricht(SpielInfo info) {
		this.spielInfo = info;
	}
}
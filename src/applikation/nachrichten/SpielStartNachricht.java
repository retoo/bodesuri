package applikation.nachrichten;

import java.util.Vector;

import pd.spieler.Partnerschaft;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht die allen Spielern meldet dass das Spiel nun beginnt.
 */
public class SpielStartNachricht extends Nachricht {
	/**
	 * Namen aller teilnehmenden Spieler
	 */
	public final SpielInfo spielInfo;
	public final Vector<Partnerschaft> partnerschaften;

	public SpielStartNachricht(SpielInfo info, Vector<Partnerschaft> partnerschaften) {
		this.spielInfo = info;
		this.partnerschaften = partnerschaften;
	}
}
package applikation.nachrichten;

import java.util.Vector;

import pd.spiel.spieler.Partnerschaft;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Server dem Client, dass das Spiel startet.
 */
public class SpielStartNachricht extends Nachricht {
	/** Namen aller teilnehmenden Spieler */
	public final SpielInfo spielInfo;
	public final Vector<Partnerschaft> partnerschaften;

	public SpielStartNachricht(SpielInfo info, Vector<Partnerschaft> partnerschaften) {
		this.spielInfo = info;
		this.partnerschaften = partnerschaften;
	}
}
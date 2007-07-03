package applikation.nachrichten;

import java.util.List;

import pd.spiel.spieler.Partnerschaft;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Server dem Client, dass das Spiel startet.
 */
public class SpielStartNachricht extends Nachricht {
	/** Namen aller teilnehmenden Spieler */
	public final SpielInfo spielInfo;
	public final List<Partnerschaft> partnerschaften;

	public SpielStartNachricht(SpielInfo info, List<Partnerschaft> partnerschaften) {
		this.spielInfo = info;
		this.partnerschaften = partnerschaften;
	}
}
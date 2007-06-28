package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Server dem Client den Beitritt zu einem Spiel bestätigt.
 */
public class BeitrittsInformation extends Nachricht {
	/** Namen aller Spieler die bereits beigetreten sind. */
	public final SpielInfo spielInfo;

	public BeitrittsInformation(SpielInfo spielInfo) {
		this.spielInfo = spielInfo;
	}
}

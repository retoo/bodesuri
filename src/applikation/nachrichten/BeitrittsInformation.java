package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die dem Spieler den Beitritt zum Spiel bestätigt.
 */
public class BeitrittsInformation extends Nachricht {
	/**
	 * Name der bereits
	 */
	public final SpielInfo spielInfo;

	/**
	 * @param spielInfo
	 */
	public BeitrittsInformation(SpielInfo spielInfo) {
		this.spielInfo = spielInfo;
	}
}

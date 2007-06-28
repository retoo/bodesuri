package applikation.nachrichten;

import pd.spieler.Partnerschaft;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Server dem Client mitteilt, dass das Spiel fertig ist.
 */
public class SpielFertigNachricht extends Nachricht {
	public final Partnerschaft gewinner;

	public SpielFertigNachricht(Partnerschaft gewinner) {
		this.gewinner = gewinner;
	}
}

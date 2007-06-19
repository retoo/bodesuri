package applikation.nachrichten;

import pd.spieler.Partnerschaft;
import dienste.netzwerk.Nachricht;

public class SpielFertigNachricht extends Nachricht {
	public final Partnerschaft gewinner;

	public SpielFertigNachricht(Partnerschaft gewinner) {
		this.gewinner = gewinner;
    }
}

package applikation.server.zustaende;

import java.util.Vector;

import pd.spieler.Partnerschaft;
import pd.spieler.Spieler;
import applikation.nachrichten.SpielFertigNachricht;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ZugAbschluss extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Vector<Partnerschaft> partnerschaften = spiel.getPartnerschaften();
		
		// Spieler auf dem Server in den EndModus setzen (falls möglich)
		for ( Partnerschaft p : partnerschaften ) {
			Spieler fertigerSpieler = p.istEndModusMoeglich();
			if (fertigerSpieler != null) {
				fertigerSpieler.addPartnerFiguren();
			}
		}
		
		if (spiel.istFertig()) {
			Partnerschaft gewinner = spiel.getGewinner();
			spiel.broadcast(new SpielFertigNachricht(gewinner));

			spiel.server.ausschalten();

			return ServerEnde.class;
		} else if (spiel.runde.isFertig()) {
			return StartRunde.class;
		} else { /* ganz noraml weiter zum nächsten zug */
			return StarteZug.class;
		}
	}
}

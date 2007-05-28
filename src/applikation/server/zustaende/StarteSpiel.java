package applikation.server.zustaende;

import pd.Spiel;
import applikation.nachrichten.SpielStartNachricht;
import applikation.nachrichten.ZugAufforderung;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.zustaende.Zustand;

/**
 * Passiver Zustand der das Spiel initialisiert.
 */
public class StarteSpiel extends PassiverServerZustand {
    public Zustand handle() {
		Spielerschaft spielerschaft = automat.spielerschaft;
		
		Spiel spiel = new Spiel();		
		
		for (Spieler spieler : spielerschaft) {
			spiel.fuegeHinzu(spieler.name);
		}

		SpielStartNachricht ssn = new SpielStartNachricht(spielerschaft.getStringArray());
		
		spielerschaft.broadcast(ssn);
		
        spielerschaft.getAktuellerSpieler().endpunkt.sende(new ZugAufforderung());

		return automat.getZustand(SpieleSpiel.class);
	}
}

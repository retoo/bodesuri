package applikation.server.zustaende;

import pd.Spiel;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import applikation.server.nachrichten.SpielStartNachricht;
import applikation.server.nachrichten.ZugAufforderung;
import dienste.automat.zustaende.Zustand;

public class StarteSpiel extends PassiveServerZustand {

	@Override
    public Zustand handle() {
		Spielerschaft spielerschaft = automat.spielerschaft;
		
		Spiel spiel = new Spiel();		
		
		for (Spieler spieler : spielerschaft) {
			spiel.fuegeHinzu(spieler.spielerName);
		}

		SpielStartNachricht ssn = new SpielStartNachricht(spielerschaft.getStringArray());
		
		spielerschaft.broadcast(ssn);
		
        spielerschaft.getAktuellerSpieler().endpunkt.sende(new ZugAufforderung());

		return automat.getZustand(SpieleSpiel.class);
	}
}

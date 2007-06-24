package applikation.client.zugautomat.zustaende;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ZugautomatAbschluss extends ClientZugZustand implements
        PassiverZustand {

	public Class<? extends Zustand> handle() {
		bewegungenZuruecksetzen();
		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);

		return EndZustand.class;
	}
}

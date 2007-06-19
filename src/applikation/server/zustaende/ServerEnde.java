package applikation.server.zustaende;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

public class ServerEnde extends ServerZustand {
	Class<? extends Zustand> verbindungGeschlossen(EndPunktInterface absender) {
		spiel.entferne(absender);

		if (spiel.getAnzahlSpieler() == 0)
			return EndZustand.class;
		else
			return this.getClass();
	}
}

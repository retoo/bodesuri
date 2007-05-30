package applikation.server.zustaende;

import applikation.nachrichten.ZugAufforderung;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.zustaende.Zustand;

public class StarteZug extends PassiverServerZustand {

	@Override
	public Zustand handle() {
		/* Aktuellen Spieler bestimmen
		 * und diesem mitteilen dass er einen Zug fahren soll
		 */

		Spielerschaft spielers = automat.spielerschaft;

		spielers.rotiereSpieler();
		Spieler naechsterSpieler = spielers.getAktuellerSpieler();
		spielers.broadcast("Nächster Spieler ist " + naechsterSpieler + ".");
		naechsterSpieler.endpunkt.sende(new ZugAufforderung());

		return automat.getZustand(WarteAufZug.class);
	}
}

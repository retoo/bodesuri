package applikation.server.zustaende;

import applikation.nachrichten.ZugAufforderung;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class StarteZug extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		/* Aktuellen Spieler bestimmen
		 * und diesem mitteilen dass er einen Zug fahren soll
		 */

		Spielerschaft spielers = spielDaten.spielerschaft;

		spielers.rotiereSpieler();
		Spieler naechsterSpieler = spielers.getAktuellerSpieler();
		spielers.broadcast("Nächster Spieler ist " + naechsterSpieler + ".");

		ZugAufforderung aufforderung =new ZugAufforderung(naechsterSpieler.spieler);

		spielers.broadcast(aufforderung);

		return WarteAufZug.class;
	}
}

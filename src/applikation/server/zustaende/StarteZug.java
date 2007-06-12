package applikation.server.zustaende;

import applikation.nachrichten.ZugAufforderung;
import applikation.server.pd.Spieler;
import applikation.server.pd.Spielerschaft;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class StarteZug extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		/* Aktuellen Spieler bestimmen
		 * und diesem mitteilen dass er einen Zug fahren soll
		 */

		Spielerschaft spielers = spielDaten.spielerschaft;

		spielers.runde.rotiereSpieler();
		Spieler naechsterSpieler = spielers.runde.getAktuellerSpieler();
		spielers.broadcast("Nächster Spieler ist " + naechsterSpieler + ".");

		ZugAufforderung aufforderung =new ZugAufforderung(naechsterSpieler.spieler);

		spielers.broadcast(aufforderung);

		return WarteAufZug.class;
	}
}

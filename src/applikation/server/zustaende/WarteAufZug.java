package applikation.server.zustaende;

import applikation.nachrichten.ZugAufforderung;
import applikation.nachrichten.ZugInformation;
import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand während des Spieles. Eingende ZugInformationen werden an
 * alle Spieler verteilt.
 */
public class WarteAufZug extends AktiverServerZustand {

	Zustand zugInfo(EndPunkt absender, ZugInformation zugInfo) {
		Spielerschaft spielers = automat.spielerschaft;
		Spieler aktuellerSpieler = spielers.getAktuellerSpieler();

		if (absender != aktuellerSpieler.endpunkt) {
			spielers.broadcast("HAH.. huere michi, de " + absender
			          + " wott voll bschisse");
			throw new RuntimeException("beschiss von " + absender + " an "
			                           + aktuellerSpieler);
		}

		spielers.broadcast(zugInfo);


		System.out.println("Ausgeführter Zug: " + zugInfo.zug);

		spielers.rotiereSpieler();

		Spieler naechsterSpieler = spielers.getAktuellerSpieler();

		spielers.broadcast("Nächster Spieler ist " + naechsterSpieler + ".");

		naechsterSpieler.endpunkt.sende(new ZugAufforderung());

		return automat.getZustand(VersendeZug.class);
	}
}
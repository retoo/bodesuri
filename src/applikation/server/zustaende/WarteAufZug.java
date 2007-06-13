package applikation.server.zustaende;

import applikation.nachrichten.Aufgabe;
import applikation.nachrichten.ZugInformation;
import applikation.server.pd.Runde;
import applikation.server.pd.Spieler;
import applikation.server.pd.Spiel;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Zustand während des Spieles. Eingende ZugInformationen werden an
 * alle Spieler verteilt.
 */
public class WarteAufZug extends ServerZustand {
	Class<? extends Zustand> zugInfo(EndPunktInterface absender, ZugInformation zugInfo) {
		Spiel spielers = spiel;

		spielers.sicherStellenIstAktuellerSpieler(absender);

		spielers.broadcast(zugInfo);
		System.out.println("Ausgeführter Zug: " + zugInfo.zug);

		return VersendeZug.class;
	}

	Class<? extends Zustand> aufgabe(EndPunktInterface absender, Aufgabe aufgabe) {
		Runde runde = spiel.runde;

		Spieler aktuellerSpieler = runde.getAktuellerSpieler();

		spiel.sicherStellenIstAktuellerSpieler(absender);
		runde.entferneSpieler(aktuellerSpieler);

		return VersendeZug.class;
	}
}
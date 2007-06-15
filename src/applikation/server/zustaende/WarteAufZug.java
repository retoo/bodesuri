package applikation.server.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Zug;
import applikation.nachrichten.Aufgabe;
import applikation.nachrichten.ZugInformation;
import applikation.server.pd.Runde;
import applikation.server.pd.RundenTeilnahme;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Zustand während des Spieles. Eingende ZugInformationen werden an
 * alle Spieler verteilt.
 */
public class WarteAufZug extends ServerZustand {
	Class<? extends Zustand> zugInfo(EndPunktInterface absender, ZugInformation zugInfo) {
		spiel.sicherStellenIstAktuellerSpieler(absender);

		Runde runde = spiel.runde;
		Spieler spieler = runde.getAktuellerSpieler();
		RundenTeilnahme teilnahme = runde.getTeilname(spieler);

		try {
			Zug zug = zugInfo.zug.validiere();
			zug.ausfuehren();
        } catch (RegelVerstoss e) {
        	/* Ungültiger Zug */
        	String msg = "Ungültige Nachricht von Spieler " + spieler + " " + e;
        	spiel.broadcast(msg);
        	throw new RuntimeException(msg);
        }


        teilnahme.neuerZug(zugInfo.zug);
        spiel.broadcast(zugInfo);

		System.out.println("Ausgeführter Zug: " + zugInfo.zug);

		if (teilnahme.hatKeineKartenMehr()) {
			runde.entferneSpieler(spieler);
		}

		return VersendeZug.class;
	}

	Class<? extends Zustand> aufgabe(EndPunktInterface absender, Aufgabe aufgabe) {
		System.out.println("Aufgabe erhalten!");
		Runde runde = spiel.runde;
		Spieler spieler = runde.getAktuellerSpieler();

		spiel.sicherStellenIstAktuellerSpieler(absender);
		runde.entferneSpieler(spieler);

		return VersendeZug.class;
	}
}
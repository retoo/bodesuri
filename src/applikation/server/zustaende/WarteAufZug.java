package applikation.server.zustaende;

import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.zugsystem.Zug;
import applikation.nachrichten.Aufgabe;
import applikation.nachrichten.AufgabeInformation;
import applikation.nachrichten.ZugInformation;
import applikation.server.pd.Runde;
import applikation.server.pd.RundenTeilnahme;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Der Server wartet, bis der {@link Spieler} seinen {@link Zug} erfasst hat. Der Spieler kann
 * entweder den erfassten Zug mittels der Nachricht {@link ZugInformation} melden oder
 * mittels der Nachricht {@link Aufgabe} aufgeben. Die restlichen {@link Spieler} werden über
 * die durchgeführte Aktion informiert.
 *
 * Hat der Spieler aufgegeben oder keine Karten mehr wird er aus der aktuellen
 * {@link Runde} entfernt.
 *
 * Hat der Server eine Spieler-Reaktion erhalten, sei es {@link ZugInformation} oder {@link Aufgabe}, wird
 * in den Zustand {@link ZugAbschluss} gewechselt.
 */
public class WarteAufZug extends ServerZustand {
	/**
	 * Verarbeitet die ZugInfo eines Spielers. Ist der Zug gültig wird
	 * er allen Spielern mitgeteilt.
	 */
	Class<? extends Zustand> zugInfo(EndPunktInterface absender, ZugInformation zugInfo) {
		spiel.sicherStellenIstAktuellerSpieler(absender);

		Runde runde = spiel.runde;
		Spieler spieler = runde.getAktuellerSpieler();
		RundenTeilnahme teilnahme = runde.getTeilname(spieler);


		/* Zug validieren */
		try {
			Zug zug = zugInfo.zug.validiere();
			zug.ausfuehren();
        } catch (RegelVerstoss e) {
        	/* Ungültiger Zug */
        	String msg = "Ungültige Nachricht von Spieler " + spieler + " " + e;
        	throw new RuntimeException(msg);
        }

        teilnahme.neuerZug(zugInfo.zug);
        spiel.broadcast(zugInfo);

        /* Sollte der betreffende Spieler keine Karte mehr haben wird er
         * aus der aktuellen Runde entfernt.
         */
		if (teilnahme.hatKeineKartenMehr()) {
			runde.entferneSpieler(spieler);
		}

		return ZugAbschluss.class;
	}

	/**
	 * Sollte der Spieler aufgeben wird dies in diesem Handle verarbeitet.
	 */
	Class<? extends Zustand> aufgabe(EndPunktInterface absender, Aufgabe aufgabe) {
		spiel.sicherStellenIstAktuellerSpieler(absender);

		Runde runde = spiel.runde;
		Spieler spieler = runde.getAktuellerSpieler();

		/* Spieler aus der aktuellen Runde entfernen */
		runde.entferneSpieler(spieler);
		spiel.broadcast(new AufgabeInformation(spieler.spieler));

		return ZugAbschluss.class;
	}
}
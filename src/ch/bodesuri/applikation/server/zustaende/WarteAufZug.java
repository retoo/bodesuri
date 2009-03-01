/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ch.bodesuri.applikation.server.zustaende;

import ch.bodesuri.applikation.nachrichten.Aufgabe;
import ch.bodesuri.applikation.nachrichten.AufgabeInformation;
import ch.bodesuri.applikation.nachrichten.ZugInformation;
import ch.bodesuri.applikation.server.pd.Runde;
import ch.bodesuri.applikation.server.pd.RundenTeilnahme;
import ch.bodesuri.applikation.server.pd.Spieler;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;
import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;
import ch.bodesuri.pd.zugsystem.Zug;

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
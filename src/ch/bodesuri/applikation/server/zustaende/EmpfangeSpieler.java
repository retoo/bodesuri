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

import ch.bodesuri.applikation.nachrichten.BeitrittVerweigert;
import ch.bodesuri.applikation.nachrichten.BeitrittsInformation;
import ch.bodesuri.applikation.nachrichten.SpielBeitreten;
import ch.bodesuri.applikation.nachrichten.SpielerInfo;
import ch.bodesuri.applikation.server.pd.Spiel;
import ch.bodesuri.applikation.server.pd.Spieler;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;

/**
 * Der Server empfängt solange neue {@link Spieler} bis das {@link Spiel} vollzählig ist.
 *
 * Erhält der Server die Nachricht {@link SpielBeitreten} prüft er ob bereits ein
 * Spieler dem angegebenen Namen im Spiel vorhanden ist. Falls nicht, bestätigt
 * er den Beitritt mit der Nachricht {@link SpielBeitreten} und informiert die
 * restlichen Spieler über den Neuling mit der Nachricht {@link BeitrittsInformation}.
 *
 * Sobald alle Spieler komplett sind, wird in den Zustand {@link SpielStart}
 * gewechselt.
 */
public class EmpfangeSpieler extends ServerZustand {
	Class<? extends Zustand> spielBeitreten(EndPunktInterface absender,
	                                        SpielBeitreten beitreten) {

		/* Verweigere den Zutritt wenn es bereits einen Spieler mit diesem Namen
		 * gibt.
		 */
		if (spiel.hatBereitsSpieler(beitreten.spielerName)) {
			BeitrittVerweigert bv = new BeitrittVerweigert(
					"Es hat bereits ein Spieler mit dem Namen "
							+ beitreten.spielerName
							+ ". Bitte wähle einen anderen Namen und versuche es erneut.");

			absender.sende(bv);

			absender.ausschalten();
			return this.getClass();
		}

		spiel.fuegeHinzu(beitreten.spielerName, absender);
		spiel.broadcast(new BeitrittsInformation(spiel.getSpielInfo()));

		String status = "Verbundene Spieler:\n<ul>\n";
		for (SpielerInfo spielerInfo : spiel.getSpielInfo().spielers) {
			status += "<li>" + spielerInfo.spielername + "</li>\n";
		}
		status += "</ul>\n";
		spiel.status.schreibeStatus(status);

		if (spiel.isKomplett()) {
			spiel.partnerschaftenBilden();

			return SpielStart.class;
		}

		return EmpfangeSpieler.class;
	}
}

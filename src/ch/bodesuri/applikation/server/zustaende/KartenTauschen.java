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

import ch.bodesuri.applikation.nachrichten.KartenTausch;
import ch.bodesuri.applikation.server.pd.Runde;
import ch.bodesuri.applikation.server.pd.Spieler;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;
import ch.bodesuri.pd.regelsystem.Karte;

/**
 * Alle {@link Spieler} müssen mit ihrem Partner eine {@link Karte} tauschen. Der Server wartet
 * bis alle Spieler die zu tauschende Karte mit der Nachricht {@link KartenTausch}
 * gemeldet haben. Anschliessend wird die getauschte Karte mit derselben
 * Nachricht dem Partner gemeldet und es wird in den Zustand {@link StarteZug}
 * gewechselt.
 *
 */
public class KartenTauschen extends ServerZustand {
	Class<? extends Zustand> kartenTausch(EndPunktInterface absender, KartenTausch tausch) {
		Spieler spieler = spiel.getSpieler(absender);

		if (spieler == null) {
			throw new RuntimeException("Unbekannter Spieler, kann Spieler nicht anhand des Endpunktes "
			                                   + absender + " auflösen");
		}

		Runde runde = spiel.runde;

		runde.tausche(spieler, tausch);

		if (runde.isFertigGetauscht()) {
			runde.tauscheAbschluss();
			return StarteZug.class;
		}

		return KartenTauschen.class;
	}
}

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


package applikation.server.zustaende;

import applikation.nachrichten.RundenStart;
import applikation.server.pd.Runde;
import applikation.server.pd.RundenTeilnahme;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Eine neue {@link Runde} wird gestartet und mit der Nachricht {@link RundenStart} den
 * Spielern mitgeteilt. Es werden allen Spielern Karten ausgeteilt.
 */
public class StartRunde extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Runde runde = spiel.starteRunde();

		for (RundenTeilnahme teilnahme: runde.getTeilnamhmen()) {
			Spieler spieler = teilnahme.getSpieler();
			spieler.sende( new RundenStart(teilnahme.getKarten()) );
		}

		return KartenTauschen.class;
	}
}

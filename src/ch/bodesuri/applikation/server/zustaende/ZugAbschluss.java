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

import ch.bodesuri.applikation.nachrichten.SpielFertigNachricht;
import ch.bodesuri.applikation.server.pd.Runde;
import ch.bodesuri.applikation.server.pd.Spiel;
import ch.bodesuri.dienste.automat.zustaende.PassiverZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.pd.spiel.spieler.Partnerschaft;
import ch.bodesuri.pd.zugsystem.Zug;


/**
 * Server schliesst den {@link Zug} formell ab. Es wird geprüft, ob das {@link Spiel}
 * beziehungsweise die {@link Runde} abgeschlossen ist und in den Zustand {@link ServerStoppen}
 * respektive {@link StartRunde} gewechselt. Ist weder das {@link Spiel} noch die {@link Runde} fertig
 * wird in den Zustand {@link StarteZug} gewechselt.
 *
 * Bei Spielende wird der Gewinner mittels der Nachricht
 * {@link SpielFertigNachricht} allen Spielern mitgeteilt.
 */
public class ZugAbschluss extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		if (spiel.istFertig()) {
			/* Gewinner den restlichen Spielern melden */
			Partnerschaft gewinner = spiel.getGewinner();
			spiel.broadcast(new SpielFertigNachricht(gewinner));

			return ServerStoppen.class;
		} else if (spiel.runde.isFertig()) {
			return StartRunde.class;
		} else { /* ganz normal weiter zum nächsten zug */
			return StarteZug.class;
		}
	}
}

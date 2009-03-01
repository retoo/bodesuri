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


package ch.bodesuri.applikation.client.zustaende;

import ch.bodesuri.dienste.automat.zustaende.EndZustand;
import ch.bodesuri.dienste.automat.zustaende.PassiverZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem sich dem Automat befindet wenn wir das Spiel beenden
 * wollen. In diesem Zustand werden Events, die möglicherweise noch in der Queue
 * warten, konsumiert, das UI heruntergefahren, die Verbindung zum Server
 * geschlossen und in den {@link EndZustand} gewechselt, der den Automaten
 * beendet.
 */
public class SpielEnde extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		controller.herunterfahren();
		if (spiel.endpunkt != null) {
			spiel.endpunkt.ausschalten();
		}
		return EndZustand.class;
	}
}

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

/* Signiert durch: ecouto */

package ch.bodesuri.applikation.bot;

import java.util.List;
import java.util.Map;

import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;


/**
 * Interface eines normalen Bots. Botklassen, die dieses Interface
 * implementieren, müssen fähig sein aus einer Liste möglicher Züge einen
 * intelligenten Zug auszuwählen.
 */
public interface Bot {
	/**
	 * Liefert den gewünschten Zug des Bots zurück.
	 *
	 * @param spiel Das Spiel in welchem der Zug gemacht werden soll
	 * @param moeglich Eine Liste aller möglichen Züge
	 * @param kartenMap Eine Map die alle pd.Karten nach app.client.pd.Karten mapt.
	 * @return Der zu machende Zug oder null sollte kein Zug möglich sein.
	 */
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, ch.bodesuri.applikation.client.pd.Karte> kartenMap);
}

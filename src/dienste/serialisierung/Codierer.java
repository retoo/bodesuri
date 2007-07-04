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


package dienste.serialisierung;

import java.util.HashMap;
import java.util.Map;

/**
 * Codierer, den es pro {@link pd.spiel.Spiel} einmal gibt und der die Abbildung von
 * Codes zu Objekten verwaltet.
 *
 * Jedes codierbare Objekt hat einen eindeutigen Code zugeordnet. Wenn das
 * Objekt serialisiert wird, wird nicht das Objekt selber geschrieben, sondern
 * ein codiertes Objekt (ein Ersatzobjekt) mit einem Code.
 *
 * Das codierte Objekt wird übermittelt und auf einem anderen Rechner wird es
 * decodiert, indem in der lokalen Tabelle das zu diesem Code gespeicherte
 * Objekt hervorgeholt wird.
 *
 * Das urprünglich versendete Objekt entspricht dem auf dem Empfänger, da die
 * Tabelle gleich aufgebaut wurde.
 */
public class Codierer {
	private Map<String, Object> objekte;

	/**
	 * Erstellt einen Codierer.
	 */
	public Codierer() {
		objekte = new HashMap<String, Object>();
	}

	/**
	 * Speichert einen Code und das zugehörige codierbare Objekt.
	 *
	 * @param code Eindeutiger Code des Objekts
	 * @param objekt Zugehöriges Objekt, welches codierbar ist
	 */
	public void speichere(String code, Object objekt) {
		objekte.put(code, objekt);
	}

	/**
	 * Gibt von einem Code das zugehörige Objekt zurück.
	 *
	 * @param code Code des Objekts
	 * @return Objekt, das mit dem Code gespeicher wurde
	 */
	public Object get(String code) {
		return objekte.get(code);
	}
}

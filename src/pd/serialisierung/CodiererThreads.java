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


package pd.serialisierung;

import java.util.HashMap;
import java.util.Map;

import dienste.serialisierung.Codierer;

/**
 * Speichert Threads und die dazu zugewiesenen Codierer.
 */
public class CodiererThreads {
	private static Map<Thread, Codierer> codiererThreads = new HashMap<Thread, Codierer>();

	/**
	 * Registriere einen Thread mit dem dazugehörigen Codierer.
	 *
	 * @param thread Thread, der registriert werden soll
	 * @param codierer Codierer, der für den Thread verwendet werden soll
	 */
	public static void registriere(Thread thread, Codierer codierer) {
		if (codierer == null) {
			throw new RuntimeException("Registrierter Codierer darf nicht null sein");
		}
		codiererThreads.put(thread, codierer);
	}

	/**
	 * @param thread Thread, dessen Codierer gefragt ist
	 * @return Codierer, der zum Thread gehört
	 */
	public static Codierer getCodierer(Thread thread) {
		return codiererThreads.get(thread);
	}
}

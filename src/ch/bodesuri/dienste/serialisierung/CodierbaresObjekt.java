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


package ch.bodesuri.dienste.serialisierung;

import java.io.Serializable;
import java.util.Observable;

/**
 * Objekt, dem ein Code zugeordnet ist und codiert werden kann.
 *
 * Das Objekt wird mithilfe des Codierers codiert, um das codierte Objekt
 * anstelle des ursprünglichen Objekts über das Netzwerk zu schicken.
 */
public abstract class CodierbaresObjekt extends Observable implements Serializable {
	private String code;

	/**
	 * Grundkonstruktor für codierbare Objekte.
	 *
	 * @param code Eindeutiger Code, der dem Objekt zugeordnet ist
	 */
	public CodierbaresObjekt(String code) {
		getCodierer().speichere(code, this);
		this.code = code;
	}

	/*
	 * Wird von ObjectOutputStream aufgerufen, um herauszufinden, was für ein
	 * Objekt anstelle dieses Objekts in den ObjectOutputStream geschrieben
	 * werden soll. Es soll ein CodiertesObjekt mit dem Code geschrieben werden.
	 */
	protected Object writeReplace() {
		return getCodiertesObjekt(code);
	}

	/**
	 * @return Codierer, der für das Codieren der Objekte zuständig ist
	 */
	protected abstract Codierer getCodierer();

	/**
	 * Gib ein konkretes codiertes Objekt zurück, das anstelle dieses Objekts
	 * serialisiert wird.
	 * 
	 * @param code Code des Objekts
	 * @return CodiertesObjekt zu diesem Code
	 */
	protected abstract CodiertesObjekt getCodiertesObjekt(String code);
}

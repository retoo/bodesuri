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

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Objekt, das einen Code enthält und serialisiert werden kann.
 *
 * Dieses Objekt wird serialisiert und über das Netzwerk geschickt. Am anderen
 * Ende wird das Objekt deserialisiert und der ObjectInputStream ruft
 * readResolve() auf.
 */
public abstract class CodiertesObjekt implements Serializable {
	private String code;

	/**
	 * Erstellt ein CodiertesObjekt.
	 *
	 * @param code
	 *            Code, der dem codierten Objekt zugeordnet ist
	 */
	public CodiertesObjekt(String code) {
		this.code = code;
	}

	/*
	 * Wird von ObjectInputStream aufgerufen, um herauszufinden, welches Objekt
	 * diesem codierten Objekt zugeordnet ist.
	 *
	 * Wenn der Codierer den Code nicht kennt, ihm also kein Objekt zugeordnet
	 * worden ist, wird eine UnbekannterCodeException geworfen.
	 */
	protected Object readResolve() throws ObjectStreamException {
		Object obj = getCodierer().get(code);
		if (obj == null) {
			throw new UnbekannterCodeException(code);
		}
		return obj;
	}

	/**
	 * @return Codierer, der die Objekte zu den Codes gespeichert hat
	 */
	protected abstract Codierer getCodierer();
}

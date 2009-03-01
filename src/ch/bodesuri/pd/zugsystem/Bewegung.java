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


package ch.bodesuri.pd.zugsystem;

import java.io.Serializable;

import ch.bodesuri.pd.spiel.brett.BankFeld;
import ch.bodesuri.pd.spiel.brett.Brett;
import ch.bodesuri.pd.spiel.brett.Feld;
import ch.bodesuri.pd.spiel.brett.SpielerFeld;


/**
 * Hilfsklasse für eine Bewegung, die ein Start- und ein Zielfeld hat.
 *
 * Aus ihr kann ein {@link Weg} berechnet werden.
 */
public class Bewegung implements Serializable {
	/** Start der Bewegung. */
	public final Feld start;

	/** Ziel der Bewegung. */
	public final Feld ziel;

	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel = ziel;
	}

	/* TODO: robin: siehe ZugEingabe#equals() (-reto) */
	public boolean equals(Object obj) {
		if (obj instanceof Bewegung) {
			Bewegung o = (Bewegung) obj;
			return start == o.start && ziel == o.ziel;
		}

		return false;
	}

	public String toString() {
		return "von " + start + " nach " + ziel;
	}

	/**
	 * Berechne den Weg dieser Bewegung. Dieser enthält alle Felder, die bei der
	 * Bewegung "berührt" werden, also inklusive Start- und Zielfeld.
	 *
	 * @return Weg, über den diese Bewegung geht
	 */
	public Weg getWeg() {
		boolean vorwaerts;

		if ((start.istRing() && ziel.istRing())) {
			int startNr = start.getNummer();
			int zielNr  =  ziel.getNummer();
			int anzahlFelder = Brett.ANZAHL_FELDER;
			int startBisZiel = (zielNr - startNr + anzahlFelder) % anzahlFelder;
			int zielBisStart = (startNr - zielNr + anzahlFelder) % anzahlFelder;
			vorwaerts = (startBisZiel <= zielBisStart);
		} else if ((start.istRing()   && ziel.istHimmel()) ||
		           (start.istHimmel() && ziel.istHimmel())) {
			vorwaerts = true;
		} else {
			return null;
		}

		Weg weg = new Weg(vorwaerts);

		Feld feld = start;
		while (feld != ziel) {
			weg.add(feld);

			if (vorwaerts) {
				if (feld.istBank() && ziel.istHimmel()
				    && ((SpielerFeld) feld).getSpieler() ==
				       ((SpielerFeld) ziel).getSpieler())
				{
					/* In Himmel eintreten */
					feld = ((BankFeld) feld).getHimmel();
				} else {
					feld = feld.getNaechstes();
				}
			} else {
				feld = feld.getVorheriges();
			}

			if (feld == null) {
				/* Kein Weg gefunden */
				return null;
			}
		}
		weg.add(feld);

		return weg;
	}
}

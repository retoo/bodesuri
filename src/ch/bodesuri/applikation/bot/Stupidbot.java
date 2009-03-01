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
import ch.bodesuri.pd.zugsystem.Bewegung;


/**
 * Ziemlich dummer Bot der zufällig aus einem der möglichen Züge auswählt.
 */
public class Stupidbot implements Bot {
	public ZugErfasstEvent macheZug(
					Spiel spiel,
					List<ZugEingabe> moeglicheZuege,
					Map<Karte, ch.bodesuri.applikation.client.pd.Karte> kartenRegister) {
		if (moeglicheZuege.isEmpty()) {
			return null;
		} else {
			int rand = (int) Math.floor(Math.random() * moeglicheZuege.size());
			ZugEingabe ze = moeglicheZuege.get(rand);
			List<Bewegung> bewegungen = ze.getBewegungen();
			ch.bodesuri.applikation.client.pd.Karte karte = kartenRegister.get(ze.getKarte());

			/* Spezialfall Joker:
			 * Der Bot erhält mit getMoeglicheZüge() eines Jokers alle möglichen
			 * Züge, da der Joker alle möglichen Regeln verodert hat. Daraus
			 * kann er aber nicht rückschliessen welche konkrete Karte er
			 * spielen soll.
			 * Da der Server beim Validieren des Jokers aber eh alle Regeln
			 * akzeptiert, ist dies kein Problem.*/
			ZugErfasstEvent zee = new ZugErfasstEvent(spiel.spielerIch, karte, karte, bewegungen);
			return zee;
		}
	}
}

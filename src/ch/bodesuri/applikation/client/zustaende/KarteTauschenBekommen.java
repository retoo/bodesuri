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

import ch.bodesuri.applikation.client.pd.Karte;
import ch.bodesuri.dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn wir auf die Karte, die wir von unserem Mitspieler erhalten,
 * warten. Wenn sie eintrifft, wird der Zustand {@link NichtAmZug} aufgerufen.
 */
public class KarteTauschenBekommen extends ClientZustand {
	public void onEntry() {
		spiel.hinweis.neuerHinweis("Warte auf Karte des Partners.", true, null);
		spiel.spielerIch.setAmZug(false);
	}

	Class<? extends Zustand> kartenTausch(ch.bodesuri.pd.regelsystem.Karte karte) {
		/*
		 * Wir erstellen hier die app.karte neu, diese wird dann nur für die
		 * jeweilige Runde verwendet (siehe auch rundenstart)
		 */
		Karte k = new Karte(karte);

		spiel.spielerIch.getKarten().add(k);
		return NichtAmZug.class;
	}

	Class<? extends Zustand> aufgegeben() {
		controller.zeigeFehlermeldung("Bitte schau zuerst was du für eine"
		                              + " Karte bekommst bevor du aufgibst!");
		return this.getClass();
	}
}

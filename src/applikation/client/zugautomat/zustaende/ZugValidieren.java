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


package applikation.client.zugautomat.zustaende;

import pd.regelsystem.karten.Sieben;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.WegLaengeVerstoss;
import applikation.client.events.ZugErfasstEvent;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Validiert den erfassten Zug.
 * <ul>
 * <li>Ist die Karte eine Sieben und es wurde noch keine 7 Felder gefahren,
 * wechseln wir zurück nach {@link StartWaehlen} um eine weitere Bewegung zu
 * erfassen.</li>
 * <li>Ist der Zug ungültig, wird eine Fehlermeldung gezeigt, das Ziel
 * deaktiviert und zurück nach {@link ZielWaehlen} gewechselt.</li>
 * <li> Ist der Zug gültig, wird er zurück an den ClientAutomat übermittelt und
 * nach {@link ZugautomatAbschluss} gewechselt.</li>
 * </ul>
 */
public class ZugValidieren extends ClientZugZustand implements PassiverZustand {

	public Class<? extends Zustand> handle() {
		ZugErfasstEvent erfassterZug = new ZugErfasstEvent(spielDaten.spiel.spielerIch,
		                                                   spielDaten.karte,
		                                                   spielDaten.konkreteKarte,
		                                                   spielDaten.getPdBewegungen());

		try {
			erfassterZug.toZugEingabe().validiere();
		} catch (RegelVerstoss e) {
			if (e instanceof WegLaengeVerstoss) {
				WegLaengeVerstoss wegverstoss = (WegLaengeVerstoss) e;
				// Ist die Karte eine Sieben und wir haben noch keine 7 Züge?
				// -> Weitere Bewegung erfassen -> Zurück zu StartWaehlen.
				if (erfassterZug.getKonkreteKarte().getKarte() instanceof Sieben &&
					wegverstoss.getIstLaenge() < 7) {
					spielDaten.felderDeaktivieren();

					spielDaten.getZiel().setGeist(true);
					spielDaten.neueBewegungHinzufuegen();

					return StartWaehlen.class;
				}
			}

			controller.zeigeFehlermeldung(e.getMessage());
			spielDaten.setZiel(null);
			return ZielWaehlen.class;
		}

		spielDaten.spiel.queue.enqueue(erfassterZug);

		return ZugautomatAbschluss.class;
	}
}

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


package applikation.client.zugautomat;

import applikation.client.controller.Controller;
import applikation.client.pd.Spiel;
import applikation.client.zugautomat.pd.SpielDaten;
import applikation.client.zugautomat.zustaende.ClientZugZustand;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.client.zugautomat.zustaende.ZielWaehlen;
import applikation.client.zugautomat.zustaende.ZugValidieren;
import applikation.client.zugautomat.zustaende.ZugautomatAbschluss;
import applikation.client.zugautomat.zustaende.ZugautomatEndZustand;
import dienste.automat.Automat;

/**
 * Der Automat zum Erfassen eines Zuges.
 * Die normale Abfolge der Zustände:
 * <ul>
 *   <li>KarteWaehlen</li>
 *   <li>StartWaehlen</li>
 *   <li>ZielWaehlen</li>
 *   <li>ZugValidieren</li>
 *   <li>ZugautomatAbschluss</li>
 *   <li>ZugAutomatEndZustand</li>
 * </ul>
 */
public class ZugAutomat extends Automat {
	private Controller controller;
	private SpielDaten spielDaten;

	public ZugAutomat(Controller controller, Spiel spiel) {
		super(spiel.konfiguration.debugMeldungen);

		this.controller = controller;
		spielDaten = new SpielDaten(spiel);

		registriere(new KarteWaehlen(controller));
		registriere(new StartWaehlen(controller));
		registriere(new ZielWaehlen(controller));
		registriere(new ZugValidieren());
		registriere(new ZugautomatAbschluss());
		registriere(new ZugautomatEndZustand());

		setStart(KarteWaehlen.class);
	}

	private void registriere(ClientZugZustand zustand) {
		zustand.setController(controller);
		zustand.setspielDaten(spielDaten);
	    super.registriere(zustand);
	}

	public String toString() {
		return "Zug-Automat";
	}
}

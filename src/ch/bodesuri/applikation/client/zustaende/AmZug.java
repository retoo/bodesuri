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

import ch.bodesuri.applikation.client.events.FeldAbgewaehltEvent;
import ch.bodesuri.applikation.client.events.FeldGewaehltEvent;
import ch.bodesuri.applikation.client.events.HoverEndeEvent;
import ch.bodesuri.applikation.client.events.HoverStartEvent;
import ch.bodesuri.applikation.client.events.KarteGewaehltEvent;
import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.events.ZugautomatEndeEvent;
import ch.bodesuri.applikation.client.pd.SteuerungsZustand;
import ch.bodesuri.applikation.client.zugautomat.ZugAutomat;
import ch.bodesuri.applikation.nachrichten.Aufgabe;
import ch.bodesuri.applikation.nachrichten.ZugInformation;
import ch.bodesuri.dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler am Zug ist. Erstellt einen {@link ZugAutomat}
 * der sich um das Erfassen und Validieren eines Zuges kümmert. Eintreffende
 * Events werden direkt an den ZugAutomaten weitergeleitet. Dieser sendet einen
 * {@link ZugErfasstEvent} wenn er fertig ist. Der Event wird versandt und der
 * Automat geht nach {@link NichtAmZug} über.
 */
public class AmZug extends ClientZustand {
    public void onEntry() {
		spiel.zugAutomat = new ZugAutomat(controller, spiel);
		spiel.zugAutomat.init();
		spiel.setSteuerungsZustand(SteuerungsZustand.AUFGEBEN);
	}

	public void onExit() {
		spiel.zugAutomat.step(new ZugautomatEndeEvent());
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		spiel.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spiel.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		spiel.zugAutomat.step(event);
		event.feld.setHover(true);
		return this.getClass();
	}

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		spiel.zugAutomat.step(event);
		event.feld.setHover(false);
		return this.getClass();
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		spiel.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> gezogen(ZugErfasstEvent erfassterZug) {
		erfassterZug.getKarte().setAusgewaehlt(false);
		spiel.spielerIch.getKarten().remove(erfassterZug.getKarte());
		spiel.endpunkt.sende(new ZugInformation(erfassterZug.toZugEingabe()));

		return NichtAmZug.class;
	}

	Class<? extends Zustand> aufgegeben() {
		if (spiel.spielerIch.kannZiehen() && !spiel.konfiguration.debugAufgabeImmerMoeglich) {
			controller.zeigeFehlermeldung("Es kann noch nicht aufgegeben "
			                              + "werden, da es noch möglich ist zu "
			                              + "ziehen.");
			return this.getClass();
		} else {
			spiel.endpunkt.sende(new Aufgabe());

			spiel.spielerIch.getKarten().setAktiv(false);
			spiel.spielerIch.getKarten().clear();
			return NichtAmZug.class;
		}
	}
}

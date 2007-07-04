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

import applikation.client.controller.Controller;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.KarteGewaehltEvent;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Karte auswählen muss. Wenn der
 * {@link KarteGewaehltEvent} eintritt, wird der Zustand {@link StartWaehlen}
 * aufgerufen.
 */
public class KarteWaehlen extends ClientZugZustand {
	public KarteWaehlen(Controller controller) {
		this.controller = controller;
	}

	public void onEntry() {
		spielDaten.spiel.setHinweis("Wähle eine Karte.");
		spielDaten.spiel.spielerIch.getKarten().setAktiv(true);
		bewegungenZuruecksetzen();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		if (event.karte.getKarte() instanceof pd.regelsystem.karten.Joker) {
			// Wenn der Spieler aus seinen Karten einen Joker auswählt die
			// Jokerauswahl anzeigen.
			controller.zeigeJokerauswahl(true);
			karteAuswaehlen(event.karte);
			return this.getClass();
		} else if (event.karte instanceof applikation.client.pd.Joker) {
			// Die Jokerauswahl gibt dann Applikations-Joker zurück. Diese
			// enthalten die Karte für die der Joker steht (konkreteKarte).
			controller.zeigeJokerauswahl(false);
			spielDaten.konkreteKarte = event.karte;
		} else {
			// Normalfall (kein Joker)
			karteAuswaehlen(event.karte);
		}
		return StartWaehlen.class;
	}

	// Solle hier eigentlich noch nicht passieren, aber man weis nie.
	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return this.getClass();
	}
}

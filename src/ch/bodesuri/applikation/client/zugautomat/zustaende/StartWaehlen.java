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


package ch.bodesuri.applikation.client.zugautomat.zustaende;

import ch.bodesuri.applikation.client.controller.Controller;
import ch.bodesuri.applikation.client.events.FeldAbgewaehltEvent;
import ch.bodesuri.applikation.client.events.FeldGewaehltEvent;
import ch.bodesuri.applikation.client.events.KarteGewaehltEvent;
import ch.bodesuri.applikation.client.pd.Feld;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.pd.regelsystem.Regel;
import ch.bodesuri.pd.regelsystem.TauschRegel;
import ch.bodesuri.pd.spiel.spieler.Figur;
import ch.bodesuri.pd.spiel.spieler.Spieler;

/**
 * Zustand wenn der Spieler das Startfeld wählen muss.
 * <ul>
 * <li>Trifft ein {@link FeldGewaehltEvent} ein wird das Feld überprüft. Macht
 * die Wahl Sinn wird nach {@link ZielWaehlen} gewechselt. Sonst bleiben wir in
 * diesem Zustand.</li>
 * <li>Trifft ein {@link KarteGewaehltEvent} ein (der Spieler hat eine andere
 * Karte ausgewählt) wird nach {@link KarteWaehlen} gewechselt.</li>
 * <li>Trifft ein {@link FeldAbgewaehltEvent} ein, wird das Brett
 * zurückgesetzt. Wir bleiben aber in diesem Zustand.</li>
 * </ul>
 */
public class StartWaehlen extends ClientZugZustand {
	public StartWaehlen(Controller controller) {
		this.controller = controller;
    }

	public void onEntry() {
		spielDaten.spiel.hinweis.neuerHinweis("Wähle das Startfeld.", true, spielDaten.spiel.spielerIch.getFarbe());
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		Feld feld = event.feld;

		Figur figur = feld.getFigur();
		Spieler spieler = spielDaten.spiel.spielerIch.getSpieler();
		if (spieler.istFertig()) {
			spieler = spieler.getPartner();
		}
		Regel regel = spielDaten.karte.getRegel();

		boolean eigeneFigur = (figur != null && figur.istVon(spieler));
		boolean tauschRegel = (figur != null && regel instanceof TauschRegel);

		/* Prüfen ob die Markierung des StartFeldes Sinn macht */
		if  (eigeneFigur || tauschRegel) {
			spielDaten.setStart(feld);
			return ZielWaehlen.class;
		} else {
			return this.getClass();
		}
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.spiel.queue.enqueue(event);
		return KarteWaehlen.class;
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		bewegungenZuruecksetzen();
		return StartWaehlen.class;
	}
}

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

import java.util.LinkedList;

import applikation.client.controller.Controller;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.HoverEndeEvent;
import applikation.client.events.HoverStartEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.events.ZugautomatEndeEvent;
import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.zugautomat.pd.Bewegung;
import applikation.client.zugautomat.pd.SpielDaten;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

/**
 * Spezifischer aktiver Zustand des ZugAutomaten. Enthält alle Events die
 * Auftreten können.
 */
public class ClientZugZustand extends Zustand {
	protected SpielDaten spielDaten;
	protected Controller controller;

	public Class<? extends Zustand> handle(Event event) {
		if (event instanceof KarteGewaehltEvent)
			return karteGewaehlt((KarteGewaehltEvent) event);
		else if (event instanceof FeldGewaehltEvent)
			return feldGewaehlt((FeldGewaehltEvent) event);
		else if (event instanceof FeldAbgewaehltEvent)
			return feldAbgewaehlt((FeldAbgewaehltEvent) event);
		else if (event instanceof HoverStartEvent)
			return hoverStart((HoverStartEvent) event);
		else if (event instanceof HoverEndeEvent)
			return hoverEnde((HoverEndeEvent) event);
		else if (event instanceof ZugautomatEndeEvent)
			return zugautomatEnde();


		return super.handle(event);
	}
	
	/* GUI Handler - Spiel */

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		return ignoriereEvent();
	}

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		return ignoriereEvent();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		return ignoriereEvent("karteGewaehlt");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return ignoriereEvent("feldGewaehlt");
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		return ignoriereEvent("feldAbgewaehlt");
	}
	
	/* Sonstiges */

	Class<? extends Zustand> zugautomatEnde() {
		return ZugautomatAbschluss.class;
    }

	/**
	 * Eine Karte als ausgewählt markieren und dabei die Markierung der vormals
	 * ausgewählten Karte entfernen.
	 * 
	 * @param karte
	 */
	protected void karteAuswaehlen(Karte karte) {
		if (spielDaten.karte != null && spielDaten.karte.istAusgewaehlt()) {
			spielDaten.karte.setAusgewaehlt(false);
		}
		spielDaten.karte = karte;
		spielDaten.konkreteKarte = karte;
		spielDaten.karte.setAusgewaehlt(true);
	}

	/**
	 * Alle Bewegungen des Bretts zurücksetzten.
	 * <ul>
	 *   <li>Geisterfiguren des 7er Zuges entfernen</li>
	 *   <li>Alle Selektionen auf dem Brett entfernen</li>
	 *   <li>Alle erfassten Bewegungen löschen</li>
	 * </ul>
	 */
	protected void bewegungenZuruecksetzen() {
		/* Geister-Figuren entfernen. */
		for (Feld f : spielDaten.spiel.getBrett().getAlleFelder())
			f.setGeist(false); /* schöner machen */

		/* Selektionen entferen. */
		spielDaten.felderDeaktivieren();
		/* Bewegungen löschen. */
		spielDaten.bewegungen = new LinkedList<Bewegung>();
		spielDaten.bewegungen.add(new Bewegung());
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setspielDaten(SpielDaten spielDaten) {
		this.spielDaten = spielDaten;
	}
}

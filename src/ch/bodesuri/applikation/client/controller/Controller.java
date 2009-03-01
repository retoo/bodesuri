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


package ch.bodesuri.applikation.client.controller;

import java.util.List;

import ch.bodesuri.applikation.client.events.AufgegebenEvent;
import ch.bodesuri.applikation.client.events.BeendeEvent;
import ch.bodesuri.applikation.client.events.ChatEingabeEvent;
import ch.bodesuri.applikation.client.events.FeldAbgewaehltEvent;
import ch.bodesuri.applikation.client.events.FeldGewaehltEvent;
import ch.bodesuri.applikation.client.events.HoverEndeEvent;
import ch.bodesuri.applikation.client.events.HoverStartEvent;
import ch.bodesuri.applikation.client.events.KarteGewaehltEvent;
import ch.bodesuri.applikation.client.events.KartenTauschBestaetigtEvent;
import ch.bodesuri.applikation.client.events.VerbindeEvent;
import ch.bodesuri.applikation.client.pd.Chat;
import ch.bodesuri.applikation.client.pd.Feld;
import ch.bodesuri.applikation.client.pd.Karte;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.applikation.client.pd.Spieler;
import ch.bodesuri.dienste.eventqueue.EventQueue;


/**
 * Der Controller dient zum Kanalisieren der Zugriffe zwischen der UI- und
 * Applikationsschicht. Er bietet dem UI Methoden um Ereignisse an den Automaten
 * weiterzuleiten und dem Automaten die Möglichkeit UI-Ereignisse unabhängig von
 * der Implementation des UIs auszulösen.
 */
public abstract class Controller implements Steuerung {
	protected EventQueue eventQueue;
	/**
	 * Die Verbindungsdaten erfragen.
	 */
	public abstract void zeigeVerbinden();

	/**
	 * Teilt dem Controller mit, dass der Verbindungsaufbau abgebrochen wurde.
	 */
	public abstract void verbindungsaufbauAbgebrochen();

	/**
	 * Die Lobby anzeigen.
	 *
	 * @param spieler
	 *            Liste der Spieler
	 * @param chat
	 * 			  Chat
	 */
	public abstract void zeigeLobby(List<Spieler> spieler, Chat chat);

	/**
	 * Das Spielbrett anzeigen.
	 *
	 * @param spiel
	 */
	public abstract void zeigeSpiel(Spiel spiel);

	/**
	 * Die Auswahl der Karte für die der Joker steht.
	 *
	 * @param aktiv
	 */
	public abstract void zeigeJokerauswahl(boolean aktiv);

	/**
	 * Fehlermeldungen, die vom Automaten an den Controller gereicht werden,
	 * darstellen.
	 *
	 * @param fehlermeldung
	 *            anzuzeigende Fehlermeldung
	 */
	public abstract void zeigeFehlermeldung(String fehlermeldung);

	/**
	 * Meldungen, die vom Automaten an den Controller gereicht werden,
	 * darstellen.
	 *
	 * @param meldung
	 *            anzuzeigende Meldung
	 */
	public abstract void zeigeMeldung(String meldung);
	
	/**
	 * Das UI beenden.
	 */
	public abstract void herunterfahren();

	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent ve = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(ve);
	}

	public void karteAuswaehlen(Karte gewaehlteKarte) {
		KarteGewaehltEvent kge = new KarteGewaehltEvent(gewaehlteKarte);
		eventQueue.enqueue(kge);
	}

	public void kartenTauschBestaetigen() {
		KartenTauschBestaetigtEvent ktbe = new KartenTauschBestaetigtEvent();
		eventQueue.enqueue(ktbe);
	}

	public void feldAuswaehlen(Feld gewaehltesFeld) {
		FeldGewaehltEvent fge = new FeldGewaehltEvent(gewaehltesFeld);
		eventQueue.enqueue(fge);
	}

	public void feldAbwaehlen() {
		FeldAbgewaehltEvent fage = new FeldAbgewaehltEvent();
		eventQueue.enqueue(fage);
	}

	public void aufgeben() {
		AufgegebenEvent age = new AufgegebenEvent();
		eventQueue.enqueue(age);
	}

	public void hoverStart(Feld feld) {
		HoverStartEvent hse = new HoverStartEvent(feld);
		eventQueue.enqueue(hse);
	}

	public void hoverEnde(Feld feld) {
		HoverEndeEvent hee = new HoverEndeEvent(feld);
		eventQueue.enqueue(hee);
	}

	public void chatNachricht(String text) {
		ChatEingabeEvent cee = new ChatEingabeEvent(text);
		eventQueue.enqueue(cee);
    }

	public void beenden() {
		BeendeEvent be = new BeendeEvent();
		eventQueue.enqueue(be);
	}

	/**
	 * Den implementierenden Controller zum Zug auffordern.
	 */
	public void zugAufforderung() {
    }

	/**
	 * Karten für den Tausch auswählen.
	 */
	public void karteTauschenAuswaehlen() {
    }
}
package applikation.client.controller;

import java.util.List;

import pd.zugsystem.ZugEingabe;
import applikation.client.events.AufgegebenEvent;
import applikation.client.events.ChatEingabeEvent;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.HoverEndeEvent;
import applikation.client.events.HoverStartEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.events.VerbindeEvent;
import applikation.client.pd.Chat;
import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import dienste.eventqueue.EventQueue;

/**
 * Der Controller dient zum Kanalisieren der Zugriffe zwischen der UI- und
 * Applikationsschicht. Er bietet dem UI Methoden Ereignisse an den Automaten
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

	public abstract void zeigeMeldung(String meldung);

	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent ve = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(ve);
	}

	public void karteAuswaehlen(Karte gewaehlteKarte) {
		KarteGewaehltEvent kge = new KarteGewaehltEvent(gewaehlteKarte);
		eventQueue.enqueue(kge);
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

	public void zugWurdeGemacht(ZugEingabe zug) {
    }

	public void zugAufforderung() {
    }

	public void karteTauschenAuswaehlen() {
    }

	public void beenden() {
//		SpielEndeEvent see = new SpielEndeEvent();
//		eventQueue.enqueue(see);
		System.exit(0);		// TODO: Pascal: ggf. Event werfen
		// TODO: Pascal: ich würde hier nix machen, der automat soll isch selber sauber aufräumen. (-reto)
		// Kontaktiere mich sonst
	}
}
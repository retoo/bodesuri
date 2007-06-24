package applikation.client.zugautomat.zustaende;

import pd.regelsystem.Regel;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;
import applikation.client.controller.Controller;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.HoverEndeEvent;
import applikation.client.events.HoverStartEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.pd.Feld;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Bewegung machen muss. Wenn ein
 * {@link FeldGewaehltEvent} eintrifft wird der gesamte Zug validiert und
 * versendet. Der Automat wird dann beendet.
 */
public class ZielWaehlen extends ClientZugZustand {
	public ZielWaehlen(Controller controller) {
		this.controller = controller;
	}

	public void onEntry() {
		spielDaten.spiel.setHinweis("Wähle das Zielfeld.");
		spielDaten.spiel.setZaehler(0);
	}

	public void onExit() {
		spielDaten.spiel.setZaehler(-1);
		spielDaten.weg.setAktuellerWeg(null);
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		Feld feld = event.feld;

		if (spielDaten.getStart() == feld) {
			/* Dasselbe Feld nochmals angeklickt */

			spielDaten.setStart(null);

			return StartWaehlen.class;
		} else if /* Prüfen ob der Spieler eine andere Figur aus seinem eigenen Lager angelickt hat */
				(feld.istLager() && /* ist ein lager */
				 feld.getFigur() != null  && /* hat figur drauf */
				 feld.getFigur().istVon(spielDaten.spiel.spielerIch.getSpieler())) { /* gehört mir */

			spielDaten.setStart(feld);

			return this.getClass();
		} else {
			spielDaten.setZiel(feld);
			return ZugValidieren.class;
		}
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		bewegungenZuruecksetzen();
		return StartWaehlen.class;
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.spiel.queue.enqueue(event);
		return KarteWaehlen.class;
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		/* Hover anschalten */
		event.feld.setHover(false);

		Regel regel = spielDaten.karte.getRegel();

		/* Phiilippe: JOKERPROBLEM Wie bauen wir hier sauber den Joker ein? Ich hoffe es benötigt keine
		 * Fallunterscheidung (-reto)
		 */
		if (regel != null && regel.arbeitetMitWeg()) {
			/* Weg markieren */
			Bewegung bewegung = new Bewegung(spielDaten.getStart().getFeld(),
			                                 event.feld.getFeld());

			Weg weg = bewegung.getWeg();

			if (weg != null) {
				spielDaten.weg.setAktuellerWeg(weg);
				spielDaten.spiel.setZaehler(weg.getWegLaenge());
			}
		}

		return this.getClass();
	}

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		/* Hover abschalten*/
		event.feld.setHover(false);

		/* Weg nicht mehr markieren */
		return this.getClass();
	}
}

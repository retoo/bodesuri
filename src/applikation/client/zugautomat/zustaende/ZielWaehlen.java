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
 * Zustand wenn der Spieler das Zielfeld wählen muss.
 * <ul>
 * <li>Trifft ein {@link FeldGewaehltEvent} ein wird das Feld überprüft.
 * <ul>
 * <li>Macht die Wahl Sinn wird nach {@link ZugValidieren} gewechselt.</li>
 * <li>Wurde das Startfeld ausgewählt wird das Startfeld wieder deaktiviert und
 * nach {@link StartWaehlen} gewechselt.</li>
 * </ul>
 * </li>
 * <li>Trifft ein {@link KarteGewaehltEvent} ein (der Spieler hat eine andere
 * Karte ausgewählt) wird nach {@link KarteWaehlen} gewechselt.</li>
 * <li>Trifft ein {@link FeldAbgewaehltEvent} ein, wird das Brett
 * zurückgesetzt. Wir bleiben aber in diesem Zustand.</li>
 * <li>{@link HoverStartEvent} und {@link HoverEndeEvent} aktiveren bzw.
 * deaktiviern das Markieren des Weges</li>
 * </ul>
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

		Regel regel = spielDaten.konkreteKarte.getRegel();
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

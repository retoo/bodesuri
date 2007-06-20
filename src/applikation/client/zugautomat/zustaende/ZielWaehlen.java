package applikation.client.zugautomat.zustaende;

import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;
import applikation.client.controller.Controller;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.HoverEndeEvent;
import applikation.client.events.HoverStartEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.pd.Brett;
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
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		Feld feld = event.feld;

		if (spielDaten.start == feld) {
			/* Dasselbe Feld nochmals angeklickt */

			feld.setAusgewaehlt(false);

			return StartWaehlen.class;
		} else if/* Prüfen ob der Spieler eine andere Figur aus seinem eigenen Lager angelickt hat */
				(feld.istLager() && /* ist ein lager */
				 feld.getFigur() != null  && /* hat figur drauf */
				 feld.getFigur().getSpieler() == spielDaten.spiel.spielerIch.getSpieler()) { /* gehört mir */

			spielDaten.start.setAusgewaehlt(false); /* TODO: reto ins spieldaten verschieben */
			spielDaten.start = feld;
			spielDaten.start.setAusgewaehlt(true);

			return this.getClass();
		} else {
			spielDaten.ziel = feld;
			spielDaten.ziel.setAusgewaehlt(true);
			return ZugValidieren.class;
		}
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		brettZuruecksetzen();
		bewegungenZuruecksetzen();
		return StartWaehlen.class;
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		brettZuruecksetzen();
		bewegungenZuruecksetzen();
		karteAuswaehlen(event.karte);
		return StartWaehlen.class;
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		Bewegung bewegung = new Bewegung(spielDaten.start.getFeld(),
		                                 event.feld.getFeld());
		Brett brett = spielDaten.spiel.getBrett();

		Weg weg = bewegung.getWeg();

		if (weg != null) {
			for (pd.brett.Feld f : weg) {
				brett.getFeld(f).setHover(true);
			}
		}

		return this.getClass();
	}

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		Bewegung bewegung = new Bewegung(spielDaten.start.getFeld(),
		                                 event.feld.getFeld());
		Brett brett = spielDaten.spiel.getBrett();

		Weg weg = bewegung.getWeg();

		if (weg != null) {

			for (pd.brett.Feld f : weg) {
				brett.getFeld(f).setHover(false);
			}
		}

		return this.getClass();
	}
}

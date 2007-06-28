package applikation.client.zugautomat.zustaende;

import pd.regelsystem.Regel;
import pd.regelsystem.TauschRegel;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import applikation.client.controller.Controller;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.pd.Feld;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Bewegung machen muss. Wenn ein
 * {@link FeldGewaehltEvent} eintrifft wird der gesamte Zug validiert und
 * versendet. Der Automat wird dann beendet.
 */
public class StartWaehlen extends ClientZugZustand {
	public StartWaehlen(Controller controller) {
		this.controller = controller;
    }

	public void onEntry() {
		spielDaten.spiel.setHinweis("Wähle das Startfeld.");
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
			return StartWaehlen.class;
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

package applikation.client.zugautomat.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.client.controller.Controller;
import applikation.events.FeldGewaehltEvent;
import applikation.events.GezogenEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Bewegung machen muss. Wenn ein
 * {@link FeldGewaehltEvent} eintrifft wird der gesamte Zug validiert und
 * versendet. Der Automat wird dann beendet.
 */
public class EndeWaehlen extends ClientZugZustand {
	public EndeWaehlen(Controller controller) {
		this.controller = controller;
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		if (spielDaten.start == event.feld) {
			controller.feldAbwaehlen(event.feld);
			return StartWaehlen.class;
		} else {
			spielDaten.ziel = event.feld;

			/* Kann in eine seperate Methode oder in die Klasse SpielDaten verschoben werden */
			Bewegung bewegung = new Bewegung(spielDaten.start, spielDaten.ziel);
			ZugEingabe zugEingabe = new ZugEingabe(controller.getSpielerIch(),
			                                       spielDaten.karte, bewegung);
			try {
				zugEingabe.validiere();
			} catch (RegelVerstoss e) {
				controller.zeigeFehlermeldung("Ungültiger Zug: " + e.getMessage());
				return this.getClass();
			}

			spielDaten.eventQueueBodesuriClient.enqueue(new GezogenEvent(zugEingabe));

			controller.kartenAuswahl(false);
			controller.feldAuswahl(false);

			return EndZustand.class;
		}
	}


	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.karte = event.karte;
		return StartWaehlen.class;
	}
}

package applikation.client.zugautomat.zustaende;

import applikation.client.controller.Controller;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Bewegung machen muss. Wenn ein
 * {@link FeldGewaehltEvent} eintrifft wird der gesamte Zug validiert und
 * versendet. Der Automat wird dann beendet.
 */
public class EndeWaehlen extends AktiverZugZustand {
	public EndeWaehlen(Controller controller) {
		this.controller = controller;
    }

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		if (spielDaten.start == event.feld) {
			return StartWaehlen.class;
		} else {
			spielDaten.ziel = event.feld;
			return ZugValidieren.class;
		}
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.karte = event.karte;
		return StartWaehlen.class;
	}
}

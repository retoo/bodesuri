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
public class StartWaehlen extends ClientZugZustand {
	public StartWaehlen(Controller controller) {
		this.controller = controller;
    }

	public void entry() {
		spielDaten.start = null;
		spielDaten.ziel = null;
		controller.aktiviereFeld(true);
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		spielDaten.start = event.feld;
		controller.zeigeFeldauswahl(event.feld, true);
		return EndeWaehlen.class;
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.karte = event.karte;

		return this.getClass();
	}
}

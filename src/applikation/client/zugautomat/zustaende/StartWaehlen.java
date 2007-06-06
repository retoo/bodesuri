package applikation.client.zugautomat.zustaende;

import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Bewegung machen muss. Wenn ein
 * {@link FeldGewaehltEvent} eintrifft wird der gesamte Zug validiert und
 * versendet. Der Automat wird dann beendet.
 */
public class StartWaehlen extends AktiverZugZustand {
	public void entry() {
		automat.start = null;
		automat.ziel = null;
		controller.feldAuswahl(true);
	}

	Zustand feldGewaehlt(FeldGewaehltEvent event) {
		automat.start = event.feld;

		return automat.getZustand(EndeWaehlen.class);
	}
	
	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		automat.karte = event.karte;
		
		return automat.getZustand(StartWaehlen.class);
	}
}

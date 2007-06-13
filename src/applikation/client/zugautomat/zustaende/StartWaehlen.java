package applikation.client.zugautomat.zustaende;

import java.util.List;

import pd.spieler.Figur;
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

	public void onEntry() {
		spielDaten.start = null;
		spielDaten.ziel = null;
		controller.zeigeHinweis("Wähle das Startfeld");
		controller.aktiviereFeld(true);
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		// Prüfen, ob Figur auf Feld ist und ob Figur vom Spieler-Ich ist
		spielDaten.start = event.feld;
		Figur figur = event.feld.getFigur();
		List<Figur> figuren = spielDaten.spielerIch.getFiguren();
		if (figur != null && figuren.contains(figur)) {
			controller.zeigeFeldauswahl(event.feld, true);
			return ZielWaehlen.class;
		} else {
			return StartWaehlen.class;
		}
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.karte = event.karte;

		return this.getClass();
	}
}

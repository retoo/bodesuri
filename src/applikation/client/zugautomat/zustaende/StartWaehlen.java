package applikation.client.zugautomat.zustaende;

import java.util.List;

import pd.spieler.Figur;

import applikation.client.controller.Controller;
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
		spielDaten.start = null;
		spielDaten.ziel = null;
		spielDaten.spiel.setHinweis("Wähle das Startfeld.");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		Feld feld = event.feld;

		spielDaten.start = feld;
		Figur figur = feld.getFigur();
		List<Figur> figuren = spielDaten.spiel.spielerIch.getFiguren();

		// TODO: Pascal: Prüfen, ob Figur auf Feld ist und ob Figur vom Spieler-Ich
		// ist --Reto
		if (figur != null && figuren.contains(figur)) {
			feld.setAusgewaehlt(true);
			return ZielWaehlen.class;
		} else {
			feld.setAusgewaehlt(false);
			return StartWaehlen.class;
		}
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		karteAuswaehlen(event.karte);
		return this.getClass();
	}
}

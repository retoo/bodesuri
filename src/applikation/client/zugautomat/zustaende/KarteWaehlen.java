package applikation.client.zugautomat.zustaende;

import applikation.client.controller.Controller;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.KarteGewaehltEvent;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Karte auswählen muss. Wenn der
 * {@link KarteGewaehltEvent} eintritt, wird der Zustand {@link StartWaehlen}
 * aufgerufen.
 */
public class KarteWaehlen extends ClientZugZustand {
	public KarteWaehlen(Controller controller) {
		this.controller = controller;
	}

	public void onEntry() {
		spielDaten.spiel.setHinweis("Wähle eine Karte.");
		spielDaten.spiel.spielerIch.getKarten().setAktiv(true);
		bewegungenZuruecksetzen();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		/* Philippe: ups, der code hat mich grad schön verwirrt, kann man hier
		 * ein kommentar hinmachen :)? (-reto)
		 */
		if (event.karte.getKarte() instanceof pd.karten.Joker) {
			controller.zeigeJokerauswahl(true);
			karteAuswaehlen(event.karte);
			return this.getClass();
		} if (event.karte instanceof applikation.client.pd.Joker) {
			controller.zeigeJokerauswahl(false);
			spielDaten.konkreteKarte = event.karte;
		}else {
			karteAuswaehlen(event.karte);
		}
		return StartWaehlen.class;
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return this.getClass();
	}
}

package applikation.client.zugautomat.zustaende;

import pd.regelsystem.TauschRegel;
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
		pd.spieler.Spieler ich = spielDaten.spiel.spielerIch.getSpieler();

		/* Prüfen ob die Markierung des StartFeldes Sinn macht */
		if  (figur != null && /* Hat es eine Figur ? */
			(figur.getSpieler() == ich || /* Die Figur ghört dem eigenen Spieler ODER es ist eine Tauschregel*/
			spielDaten.karte.getRegel() instanceof TauschRegel)) {
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

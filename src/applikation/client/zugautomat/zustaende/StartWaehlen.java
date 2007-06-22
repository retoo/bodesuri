package applikation.client.zugautomat.zustaende;

import pd.regelsystem.TauschRegel;
import pd.spieler.Figur;
import pd.spieler.Spieler;
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
		spielDaten.spiel.setHinweis("Wähle das Startfeld.");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		Feld feld = event.feld;
		spielDaten.setStart(feld);

		Figur figur = feld.getFigur();
		Spieler spieler = spielDaten.spiel.spielerIch.spieler;
		if (spieler.istFertig()) {
			spieler = spieler.getPartner();
		}

		/* Prüfen ob die Markierung des StartFeldes Sinn macht */
		if  (figur != null && figur.istVon(spieler) || /* Die Figur ghört dem Spieler (oder dem Partner, falls im Endmodus) */
			spielDaten.karte.getRegel() instanceof TauschRegel) { /* ODER es ist eine Tauschregel */
				feld.setAusgewaehlt(true);
				return ZielWaehlen.class;
		} else {
			feld.setAusgewaehlt(false);
			return StartWaehlen.class;
		}
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.spiel.queue.enqueue(event);
		return KarteWaehlen.class;
	}
}

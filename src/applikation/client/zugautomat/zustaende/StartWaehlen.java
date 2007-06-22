package applikation.client.zugautomat.zustaende;

import java.util.List;

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
		spielDaten.spiel.setHinweis("Wähle das Startfeld.");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		Feld feld = event.feld;
		spielDaten.setStart(feld);

		Figur figur = feld.getFigur();
		List<Figur> figuren = spielDaten.spiel.spielerIch.getFiguren();

		/* Prüfen ob die Markierung des StartFeldes Sinn macht */
		if  (figur != null && (figuren.contains(figur) || /* Die Figur ghört dem eigenen Spieler (auch Partnerfiguren im Endmodus!) */
			spielDaten.karte.getRegel() instanceof TauschRegel)) { /* ODER es ist eine Tauschregel */
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

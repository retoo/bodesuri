package applikation.client.zugautomat.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.client.controller.Controller;
import applikation.events.GezogenEvent;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

public class ZugValidieren extends ClientZugZustand implements PassiverZustand {
	public ZugValidieren(Controller controller) {
		this.controller = controller;
    }

	public Class<? extends Zustand> handle() {
		Bewegung bewegung = new Bewegung(spielDaten.start, spielDaten.ziel);
		ZugEingabe zugEingabe = new ZugEingabe(controller.getSpielerIch(),
		                                       spielDaten.karte, bewegung);
		try {
			zugEingabe.validiere();
		} catch (RegelVerstoss e) {
			System.out.println("Ungültiger Zug: " + e);
			return KarteWaehlen.class;
		}
		spielDaten.eventQueueBodesuriClient.enqueue(new GezogenEvent(zugEingabe));

		controller.kartenAuswahl(false);
		controller.feldAuswahl(false);

		return EndZustand.class;
	}
}

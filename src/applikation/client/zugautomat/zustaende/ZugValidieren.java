package applikation.client.zugautomat.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.events.GezogenEvent;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

public class ZugValidieren extends PassiverZugZustand {
	public Zustand handle() {
		Bewegung bewegung = new Bewegung(automat.start, automat.ziel);
		ZugEingabe zugEingabe = new ZugEingabe(automat.spielerIch,
		                                       automat.karte, bewegung);
		try {
			zugEingabe.validiere();
		} catch (RegelVerstoss e) {
			System.out.println("Ungültiger Zug: " + e);
			return automat.getZustand(KarteWaehlen.class);
		}
		automat.eventQueueBodesuriClient.enqueue(new GezogenEvent(zugEingabe));

		return automat.getZustand(EndZustand.class);
	}
}

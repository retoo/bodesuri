package applikation.client.zugautomat.zustaende;

import pd.karten.Sieben;
import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.WegLaengeVerstoss;
import applikation.client.events.ZugErfasstEvent;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ZugValidieren extends ClientZugZustand implements PassiverZustand {

	public Class<? extends Zustand> handle() {
		ZugErfasstEvent erfassterZug = new ZugErfasstEvent(spielDaten.spiel.spielerIch,
		                                                   spielDaten.karte,
		                                                   spielDaten.konkreteKarte,
		                                                   spielDaten.getPdBewegungen());

		try {
			erfassterZug.toZugEingabe().validiere();
		} catch (RegelVerstoss e) {
			if (e instanceof WegLaengeVerstoss) {
				WegLaengeVerstoss wegverstoss = (WegLaengeVerstoss) e;
				// Ist die Karte eine Sieben und wir haben noch keine 7 Züge?
				// -> Weiteren Zug erfassen -> Zurück zu StartWaehlen.
				if (erfassterZug.getKonkreteKarte().getKarte() instanceof Sieben &&
					wegverstoss.getIstLaenge() < 7) {
					spielDaten.felderDeaktivieren();

					spielDaten.getZiel().setGeist(true);
					spielDaten.neueBewegungHinzufuegen();

					return StartWaehlen.class;
				}
			}

			controller.zeigeFehlermeldung(e.getMessage());
			spielDaten.setZiel(null);
			return ZielWaehlen.class;
		}

		spielDaten.spiel.queue.enqueue(erfassterZug);

		return ZugautomatAbschluss.class;
	}
}

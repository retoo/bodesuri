package applikation.client.zugautomat.zustaende;

import pd.karten.Sieben;
import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.WegLaengeVerstoss;
import applikation.client.events.ZugErfasstEvent;
import dienste.automat.zustaende.EndZustand;
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

				/*
				 * Philippe: JOKERPROBLEM Wenn die Karte über den Jker geholt
				 * wurde ist getkare nicht sieben, dan musst du ggf. halt
				 * konkreteKarte() verwendeen (-reto)
				 */
				if (erfassterZug.getKarte().getKarte() instanceof Sieben &&
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

		bewegungenZuruecksetzen();
		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);
		return EndZustand.class;
	}
}

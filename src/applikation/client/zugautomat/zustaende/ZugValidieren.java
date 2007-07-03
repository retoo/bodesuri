package applikation.client.zugautomat.zustaende;

import pd.regelsystem.karten.Sieben;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.WegLaengeVerstoss;
import applikation.client.events.ZugErfasstEvent;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Validiert den erfassten Zug.
 * <ul>
 * <li>Ist die Karte eine Sieben und es wurde noch keine 7 Felder gefahren,
 * wechseln wir zurück nach {@link StartWaehlen} um eine weitere Bewegung zu
 * erfassen.</li>
 * <li>Ist der Zug ungültig, wird eine Fehlermeldung gezeigt, das Ziel
 * deaktiviert und zurück nach {@link ZielWaehlen} gewechselt.</li>
 * <li> Ist der Zug gültig, wird er zurück an den ClientAutomat übermittelt und
 * nach {@link ZugautomatAbschluss} gewechselt.</li>
 * </ul>
 */
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
				// -> Weitere Bewegung erfassen -> Zurück zu StartWaehlen.
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

package applikation.client.zugautomat.zustaende;

import pd.karten.Sieben;
import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.WegLaengeVerstoss;
import pd.zugsystem.Bewegung;
import applikation.client.events.ZugErfasstEvent;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ZugValidieren extends ClientZugZustand implements PassiverZustand {

	public Class<? extends Zustand> handle() {
		Bewegung bewegung = new Bewegung(spielDaten.start.getFeld(),
		                                 spielDaten.ziel.getFeld());
		spielDaten.bewegungen.add(bewegung);
		ZugErfasstEvent erfassterZug = new ZugErfasstEvent(spielDaten.spiel.spielerIch,
		                                                   spielDaten.karte,
		                                                   spielDaten.bewegungen);
		
		try {
			erfassterZug.toZugEingabe().validiere();
		} catch (WegLaengeVerstoss e) {
			System.out.println(e.getIstLaenge());
			if (erfassterZug.getKarte().getKarte() instanceof Sieben && e.getIstLaenge() < 7) {
				return StartWaehlen.class;
			} else {
				fehler(e.getMessage());
				return ZielWaehlen.class;
			}
		} catch (RegelVerstoss e) {
			fehler(e.getMessage());
			return ZielWaehlen.class;
		}

		spielDaten.spiel.queue.enqueue(erfassterZug);
		brettZuruecksetzen();
		bewegungenZuruecksetzen();
		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);
		return EndZustand.class;
	}

	private void fehler(String fehlermeldung) {
		controller.zeigeFehlermeldung(fehlermeldung);
		spielDaten.bewegungen.remove(spielDaten.bewegungen.size()-1);
		spielDaten.ziel.setAusgewaehlt(false);
    }
}

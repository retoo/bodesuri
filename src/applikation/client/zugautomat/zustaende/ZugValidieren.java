package applikation.client.zugautomat.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.client.zustaende.SchwererFehler;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.VerbindungWegException;

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
		try {
			automat.endpunkt.sende(new ZugInformation(zugEingabe));
		} catch (VerbindungWegException e) {
			return automat.getZustand(SchwererFehler.class);
		}
		return automat.getZustand(EndZustand.class);
	}
}

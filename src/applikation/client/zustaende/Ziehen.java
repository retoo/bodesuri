package applikation.client.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.ZugEingabe;
import applikation.client.events.BewegungEingegebenEvent;
import applikation.server.nachrichten.ZugInformation;
import applikation.zugentgegennahme.ZugEntgegennahme;
import dienste.automat.Zustand;
import dienste.netzwerk.VerbindungWegException;

public class Ziehen extends AktiverClientZustand {
	protected void init() {
		automat.zugentgegennahme = new ZugEntgegennahme(automat.queue);
	}

	Zustand bewegungEingegeben(BewegungEingegebenEvent event) {
		automat.zugentgegennahme = null;

		ZugEingabe zugEingabe = new ZugEingabe(automat.spielerIch,
		                                       automat.karte, event.bewegung);
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

		return automat.getZustand(NichtAmZug.class);
	}
}

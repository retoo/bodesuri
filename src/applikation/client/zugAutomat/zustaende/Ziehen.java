package applikation.client.zugAutomat.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.ZugEingabe;
import applikation.client.zustaende.SchwererFehler;
import applikation.events.BewegungEingegebenEvent;
import applikation.nachrichten.ZugInformation;
import applikation.zugentgegennahme.ZugEntgegennahme;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.VerbindungWegException;

/**
 * Zustand wenn der Spieler eine Bewegung machen muss. Wenn ein
 * {@link BewegungEingegebenEvent} eintrifft wird der gesamte Zug validiert und
 * versendet. Der Automat wird dann beendet.
 */
public class Ziehen extends AktiverZugZustand {
	public void entry() {
		automat.zugentgegennahme = new ZugEntgegennahme(automat.queue);
		automat.aufgabe = true;
	}

	Zustand bewegungEingegeben(BewegungEingegebenEvent event) {
		// TODO Mouse-Handler bereits der UI(MouseAdapter) deaktivieren.
		// TODO Entry & exit besser nutzen.
		automat.zugentgegennahme = null;
		automat.aufgabe = false;

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

		return automat.getZustand(EndZustand.class);
	}
}

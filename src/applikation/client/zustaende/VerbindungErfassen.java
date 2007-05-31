package applikation.client.zustaende;

import java.io.IOException;
import java.net.UnknownHostException;

import applikation.events.VerbindeEvent;
import applikation.nachrichten.SpielBeitreten;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.EndPunkt;

/**
 * Zustand wenn der Spieler die Verbindungsdaten eingeben muss. Wenn ein
 * {@link VerbindeEvent} eintrifft wir der Zustand {@link VerbindungSteht}
 * aufgerufen.
 */
public class VerbindungErfassen extends AktiverClientZustand {
	Zustand verbinden(VerbindeEvent ve) {

		try {
			BriefKastenInterface briefkasten = new BriefkastenAdapter(automat.queue);

			automat.endpunkt = new EndPunkt(ve.hostname, ve.port, briefkasten);
			automat.endpunkt.sende(new SpielBeitreten(ve.spielerName));
			automat.spielerName = ve.spielerName;
		} catch (UnknownHostException e) {
			automat.meldeFehler("Unbekannter Hostname " + ve.hostname);
			automat.endpunkt = null;
			return this;
		} catch (IOException e) {
			automat.meldeFehler("Verbindung konnte nicht hergestellt werden: "
			                    + e.getMessage());
			automat.endpunkt = null;
			return this;
		}

		return automat.getZustand(VerbindungSteht.class);
	}
}

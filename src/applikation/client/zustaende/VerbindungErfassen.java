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
public class VerbindungErfassen extends ClientZustand {
	Class<? extends Zustand> verbinden(VerbindeEvent ve) {
		try {
			BriefKastenInterface briefkasten = new BriefkastenAdapter(spielDaten.queue);

			spielDaten.endpunkt = new EndPunkt(ve.hostname, ve.port, briefkasten, spielDaten);
			spielDaten.endpunkt.sende(new SpielBeitreten(ve.spielerName));
			controller.setSpielerName( ve.spielerName );
		} catch (UnknownHostException e) {
			controller.zeigeFehlermeldung("Unbekannter Hostname: " + ve.hostname);
			spielDaten.endpunkt = null;
			return VerbindungErfassen.class;
		} catch (IOException e) {
			controller.zeigeFehlermeldung("Verbindung konnte nicht hergestellt werden: "
			                    + e.getMessage());
			spielDaten.endpunkt = null;
			return VerbindungErfassen.class;
		}

		return VerbindungSteht.class;
	}
}

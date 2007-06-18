package applikation.client.zustaende;

import java.io.IOException;
import java.net.UnknownHostException;

import applikation.client.events.VerbindeEvent;
import applikation.nachrichten.SpielBeitreten;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.ClientEndPunkt;
import dienste.netzwerk.server.BriefkastenAdapter;

/**
 * Zustand wenn der Spieler die Verbindungsdaten eingeben muss. Wenn ein
 * {@link VerbindeEvent} eintrifft wir der Zustand {@link Lobby}
 * aufgerufen.
 */
public class VerbindungErfassen extends ClientZustand {
	public void onEntry() {
		controller.zeigeVerbinden(spiel.spielerName);
	}

	Class<? extends Zustand> verbinden(VerbindeEvent ve) {
		try {
			BriefKastenInterface briefkasten = new BriefkastenAdapter(spiel.queue);

			spiel.endpunkt = new ClientEndPunkt(ve.hostname, ve.port, briefkasten, spiel);
			spiel.endpunkt.sende(new SpielBeitreten(ve.spielerName));
			spiel.spielerName = ve.spielerName;
		} catch (UnknownHostException e) {
			controller.zeigeFehlermeldung("Unbekannter Hostname: " + ve.hostname);
			spiel.endpunkt = null;
			return VerbindungErfassen.class;
		} catch (IOException e) {
			controller.zeigeFehlermeldung("Verbindung konnte nicht hergestellt werden: "
			                    + e.getMessage());
			spiel.endpunkt = null;
			return VerbindungErfassen.class;
		}

		return Lobby.class;
	}
}

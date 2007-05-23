package applikation.client.zustaende;

import java.io.IOException;
import java.net.UnknownHostException;

import applikation.client.events.VerbindenEvent;
import applikation.server.nachrichten.SpielBeitreten;
import dienste.automat.Zustand;
import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.VerbindungWegException;

public class VerbindungErfassen extends AktiverClientZustand {
	Zustand verbinden(VerbindenEvent ve) {

		try {
			BriefKastenInterface briefkasten = new BriefkastenAdapter(automat.queue);
			
			automat.endpunkt = new EndPunkt(ve.hostname, ve.port, briefkasten);
			automat.endpunkt.sende(new SpielBeitreten(ve.spielerName));
			automat.spielerName = ve.spielerName;
		} catch (UnknownHostException e) {
			automat.endpunkt = null;
			return automat.getZustand(VerbindungErfassen.class);
		}  catch (VerbindungWegException e) {
			automat.endpunkt = null;
			return automat.getZustand(VerbindungErfassen.class);
		} catch (IOException e) {
			automat.endpunkt = null;
			return automat.getZustand(VerbindungErfassen.class);
		}
		
		return automat.getZustand(VerbindungWirdAufgebaut.class);
	}
}

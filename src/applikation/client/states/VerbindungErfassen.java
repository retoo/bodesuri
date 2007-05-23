package applikation.client.states;

import java.io.IOException;
import java.net.UnknownHostException;

import applikation.client.events.VerbindenEvent;
import applikation.server.nachrichten.SpielBeitreten;
import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.VerbindungWegException;
import dienste.statemachine.State;

public class VerbindungErfassen extends ActiveClientState {
	State verbinden(VerbindenEvent ve) {

		try {
			BriefKastenInterface briefkasten = new BriefkastenAdapter(automat.queue);
			
			automat.endpunkt = new EndPunkt(ve.hostname, ve.port, briefkasten);
			automat.endpunkt.sende(new SpielBeitreten(ve.spielerName));
			automat.spielerName = ve.spielerName;
		} catch (UnknownHostException e) {
			automat.endpunkt = null;
			return automat.getState(VerbindungErfassen.class);
		}  catch (VerbindungWegException e) {
			automat.endpunkt = null;
			return automat.getState(VerbindungErfassen.class);
		} catch (IOException e) {
			automat.endpunkt = null;
			return automat.getState(VerbindungErfassen.class);
		}
		
		return automat.getState(VerbindungWirdAufgebaut.class);
	}
}

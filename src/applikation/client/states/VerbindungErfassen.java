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
			BriefKastenInterface briefkasten = new BriefkastenAdapter(machine.queue);
			
			machine.endpunkt = new EndPunkt(ve.hostname, ve.port, briefkasten);
			machine.endpunkt.sende(new SpielBeitreten(ve.spielerName));
			machine.spielerName = ve.spielerName;
		} catch (UnknownHostException e) {
			machine.endpunkt = null;
			return machine.getState(VerbindungErfassen.class);
		}  catch (VerbindungWegException e) {
			machine.endpunkt = null;
			return machine.getState(VerbindungErfassen.class);
		} catch (IOException e) {
			machine.endpunkt = null;
			return machine.getState(VerbindungErfassen.class);
		}
		
		return machine.getState(VerbindungWirdAufgebaut.class);
	}
}

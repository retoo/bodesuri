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

public class VerbindungErfassen extends ClientStates {
	State verbinden(VerbindenEvent ve) {

		try {
			BriefKastenInterface briefkasten = new BriefkastenAdapter(machine.queue);
			
			machine.endpunkt = new EndPunkt(ve.hostname, ve.port, briefkasten);
			machine.endpunkt.sende(new SpielBeitreten(ve.spieler));
		} catch (UnknownHostException e) {
			machine.endpunkt = null;
			return machine.getState(VerbindungErfassen.class);
		}  catch (VerbindungWegException e) {
			return machine.getState(SchwererFehlerState.class);
		} catch (IOException e) {
			return machine.getState(SchwererFehlerState.class);
		}
		
		return machine.getState(Lobby.class);
	}
}

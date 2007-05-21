package spielplatz.zustandssynchronisation.states;

import java.io.IOException;
import java.net.UnknownHostException;

import applikation.server.nachrichten.SpielBeitreten;
import spielplatz.zustandssynchronisation.events.VerbindenEvent;
import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.VerbindungWegException;

public class ProgrammStart extends BodesuriState {
	State verbinden(VerbindenEvent ve) {

		try {
			BriefKastenInterface briefkasten = new BriefkastenAdapter(machine.queue);
			
			machine.endpunkt = new EndPunkt(ve.hostname, ve.port, briefkasten);
			machine.endpunkt.sende(new SpielBeitreten(ve.spieler));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VerbindungWegException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return machine.getState(Lobby.class);
	}
}

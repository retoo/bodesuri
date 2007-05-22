package applikation.client.states;

import applikation.client.BodesuriClient;
import applikation.client.events.NetzwerkEvent;
import applikation.client.events.VerbindenEvent;
import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.NeueVerbindung;
import applikation.server.nachrichten.SpielBeitreten;
import applikation.server.nachrichten.SpielVollNachricht;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.statemachine.Event;
import dienste.statemachine.KeinUebergangException;
import dienste.statemachine.State;
import dienste.statemachine.StateMachine;

public class ClientStates extends State {
	protected BodesuriClient machine;

	public State execute(Event event) {

		if (event instanceof NetzwerkEvent) {
			NetzwerkEvent be = (NetzwerkEvent) event;

			Brief brief = be.brief;
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof SpielBeitreten)
				return spielBeitreten(brief.absender,
				                      (SpielBeitreten) nachricht);
			else if (nachricht instanceof NeueVerbindung)
				return neueVerbindeung(brief.absender);
			else if (nachricht instanceof ChatNachricht)
				return chatNachricht(brief.absender, (ChatNachricht) nachricht);
			else if (nachricht instanceof SpielVollNachricht)
				return spielVoll(brief.absender, (SpielVollNachricht) nachricht);

		} else {
			if (event instanceof VerbindenEvent)
				return verbinden((VerbindenEvent) event);
		}

		return super.execute(event);
	}

	State verbinden(VerbindenEvent event) {
		return keinUebergang();
	}

	State spielVoll(EndPunkt absender, SpielVollNachricht nachricht) {
		return keinUebergang();
	}

	State chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		return keinUebergang();
	}

	State neueVerbindeung(EndPunkt absender) {
		return keinUebergang();
	}

	State spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {
		return keinUebergang();
	}

	State keinUebergang() {
		throw new KeinUebergangException("Kein Übergang definiert in state "
		                                 + this);
	}

	@Override
    public void setMachine(StateMachine machine) {
		this.machine = (BodesuriClient) machine;
    }
}

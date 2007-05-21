package spielplatz.zustandssynchronisation.states;

import spielplatz.zustandssynchronisation.BodesuriEvent;
import spielplatz.zustandssynchronisation.Event;
import spielplatz.zustandssynchronisation.KeinUebergangException;
import spielplatz.zustandssynchronisation.VerbindenEvent;
import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.NeueVerbindung;
import applikation.server.nachrichten.SpielBeitreten;
import applikation.server.nachrichten.SpielVollNachricht;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;

public class BodesuriState extends State {

	public State execute(Event event) {
		BodesuriEvent be = (BodesuriEvent) event;
		
		Brief brief = be.brief;
		Nachricht nachricht = brief.nachricht;
		
		if (nachricht instanceof SpielBeitreten)
			return spielBeitreten(brief.absender, (SpielBeitreten) nachricht);
		else if (nachricht instanceof NeueVerbindung) 
			return neueVerbindeung(brief.absender);
		else if (nachricht instanceof ChatNachricht)
			return chatNachricht(brief.absender, (ChatNachricht) nachricht);
		else if (nachricht instanceof SpielVollNachricht) 
			return spielVoll(brief.absender, (SpielVollNachricht) nachricht);
		else if (nachricht instanceof VerbindenEvent)
			return verbinden((VerbindenEvent) nachricht);
	
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
	    throw new KeinUebergangException("Kein Übergang definiert in state " + this);
	}
}
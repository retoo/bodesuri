package applikation.server;

import applikation.client.events.NetzwerkEvent;
import applikation.server.nachrichten.NeueVerbindung;
import applikation.server.nachrichten.SpielBeitreten;
import dienste.automat.AktiverZustand;
import dienste.automat.Automat;
import dienste.automat.Event;
import dienste.automat.KeinUebergangException;
import dienste.automat.Zustand;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;


public class AktiverServerZustand extends AktiverZustand {
	protected BodesuriServer automat;
	
	public Zustand handle(Event event) {
    	if (event instanceof NetzwerkEvent) {
    		NetzwerkEvent ne = (NetzwerkEvent) event;
    
    		Brief brief = ne.brief;
    		Nachricht nachricht = brief.nachricht;
    
    		if (nachricht instanceof SpielBeitreten)
    			return spielBeitreten(brief.absender,
    			                      (SpielBeitreten) nachricht);
    		else
    			throw new RuntimeException("Unbekannte Nachricht");
    	} else {
    		/* Systemnachricht */
    
    		if (event instanceof NeueVerbindung)
    			return neueVerbindung((NeueVerbindung) event);
    	}
    
    	return null;
    }
	
	Zustand neueVerbindung(NeueVerbindung verbindung) {
		System.out.println("Neue verbindung von " + verbindung.endpunkt);
    	return this;
    }

	Zustand spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {
    	return keinUebergang();
    }

	Zustand keinUebergang() {
    	throw new KeinUebergangException("Kein Übergang definiert in state "
    	                                 + this);
    }

	@Override
    public void setAutomat(Automat automat) {
		this.automat = (BodesuriServer) automat;
    }
}

package applikation.server.zustaende;

import applikation.server.BodesuriServer;
import applikation.server.nachrichten.SpielBeitreten;
import applikation.server.nachrichten.ZugInformation;
import dienste.automat.Automat;
import dienste.automat.Event;
import dienste.automat.KeinUebergangException;
import dienste.automat.zustaende.AktiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.NetzwerkEvent;
import dienste.netzwerk.NeueVerbindung;
import dienste.netzwerk.VerbindungGeschlossen;


/**
 * Spezfischer aktiver Server Zustand
 */
public abstract class AktiverServerZustand extends AktiverZustand {
	protected BodesuriServer automat;
	
	/* (non-Javadoc)
	 * @see dienste.automat.zustaende.AktiverZustand#handle(dienste.automat.Event)
	 */
	public Zustand handle(Event event) {
    	if (event instanceof NetzwerkEvent) {
    		NetzwerkEvent ne = (NetzwerkEvent) event;
    
    		Brief brief = ne.brief;
    		Nachricht nachricht = brief.nachricht;
    
    		if (nachricht instanceof SpielBeitreten)
    			return spielBeitreten(brief.absender,
    			                      (SpielBeitreten) nachricht);
    		else if (nachricht instanceof ZugInformation) 
    			return zugInfo(brief.absender, (ZugInformation) nachricht);
    		else if (nachricht instanceof VerbindungGeschlossen)
    			return verbindungGeschlossen(brief.absender);
    		else
    			throw new RuntimeException("Unbekannte Nachricht");
    	} 	/* Systemnachrichten */ 
    	else if (event instanceof NeueVerbindung)
    			return neueVerbindung((NeueVerbindung) event);
    	
    
    	return null;
    }
	
	/* Die Handler sind bereits in den jeweiligen Event-Klassen beschrieben */
	
	Zustand verbindungGeschlossen(EndPunkt absender) {
    	return keinUebergang();
    }

	Zustand zugInfo(EndPunkt absender, ZugInformation information) {
    	return keinUebergang();
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
	
	/* (non-Javadoc)
	 * @see dienste.automat.zustaende.Zustand#setAutomat(dienste.automat.Automat)
	 */
	@Override
    public void setAutomat(Automat automat) {
		this.automat = (BodesuriServer) automat;
    }
}

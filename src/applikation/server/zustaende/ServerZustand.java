	package applikation.server.zustaende;

import applikation.nachrichten.Aufgabe;
import applikation.nachrichten.KartenTausch;
import applikation.nachrichten.SpielBeitreten;
import applikation.nachrichten.ZugInformation;
import applikation.server.SpielDaten;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.NetzwerkEvent;
import dienste.netzwerk.NeueVerbindung;
import dienste.netzwerk.VerbindungGeschlossen;


/**
 * Spezifischer aktiver Server-Zustand
 */
public abstract class ServerZustand extends Zustand {
	protected SpielDaten spielDaten;

	/* (non-Javadoc)
	 * @see dienste.automat.zustaende.AktiverZustand#handle(dienste.automat.Event)
	 */
	public Class<? extends Zustand> handle(Event event) {
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
    		else if (nachricht instanceof KartenTausch)
				return kartenTausch(brief.absender, (KartenTausch) nachricht);
    		else if (nachricht instanceof Aufgabe)
    			return aufgabe(brief.absender, (Aufgabe) nachricht);
    		else
    			throw new RuntimeException("Unbekannte Nachricht");
    	} 	/* Systemnachrichten */
    	else if (event instanceof NeueVerbindung)
    			return neueVerbindung((NeueVerbindung) event);


    	return null;
    }

	/* Die Handler sind bereits in den jeweiligen Event-Klassen beschrieben */

	Class<? extends Zustand> aufgabe(EndPunkt absender, Aufgabe aufgabe) {
	    return keinUebergang();
    }

	Class<? extends Zustand> kartenTausch(EndPunkt absender, KartenTausch tausch) {
		return keinUebergang();
    }

	Class<? extends Zustand> verbindungGeschlossen(EndPunkt absender) {
		System.out.println("Verbindung zu Client " + absender +
		                  " wurde unerwartet beendet. Server wird beendet.");
    	return EndZustand.class;
    }

	Class<? extends Zustand> zugInfo(EndPunkt absender, ZugInformation information) {
    	return keinUebergang();
    }

	Class<? extends Zustand> neueVerbindung(NeueVerbindung verbindung) {
		System.out.println("Neue Verbindung von " + verbindung.endpunkt);
    	return this.getClass();
    }

	Class<? extends Zustand> spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {
    	return keinUebergang();
    }

	public void setSpielDaten(SpielDaten spielDaten) {
	    this.spielDaten = spielDaten;
    }
}

package applikation.client.zustaende;

import pd.zugsystem.ZugEingabe;
import applikation.client.BodesuriClient;
import applikation.client.Controller;
import applikation.events.AufgegebenEvent;
import applikation.events.GezogenEvent;
import applikation.events.VerbindeEvent;
import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.ChatNachricht;
import applikation.nachrichten.KartenTausch;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.SpielBeitreten;
import applikation.nachrichten.SpielStartNachricht;
import applikation.nachrichten.SpielVollNachricht;
import applikation.nachrichten.ZugAufforderung;
import applikation.nachrichten.ZugInformation;
import dienste.automat.Automat;
import dienste.automat.Event;
import dienste.automat.KeinUebergangException;
import dienste.automat.zustaende.AktiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.NetzwerkEvent;


/**
 * Spezifischer aktiver Client-Zustand
 */
public class AktiverClientZustand extends AktiverZustand {
	protected BodesuriClient automat;
	protected Controller controller;

	public Zustand handle(Event event) {
		// TODO Mouse-Handler bereits der UI(MouseAdapter) deaktivieren.
		// TODO Entry & exit besser nutzen.
		if (event instanceof NetzwerkEvent) {
			NetzwerkEvent be = (NetzwerkEvent) event;

			Brief brief = be.brief;
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof SpielBeitreten)
				return spielBeitreten(brief.absender,
				                      (SpielBeitreten) nachricht);
			else if (nachricht instanceof ChatNachricht)
				return chatNachricht(brief.absender, (ChatNachricht) nachricht);
			else if (nachricht instanceof SpielVollNachricht)
				return spielVoll(brief.absender, (SpielVollNachricht) nachricht);
			else if (nachricht instanceof BeitrittsBestaetigung)
				return beitrittsBestaetitigung((BeitrittsBestaetigung) nachricht);
			else if (nachricht instanceof SpielStartNachricht)
				return spielStarten((SpielStartNachricht) nachricht);
			else if (nachricht instanceof ZugInformation)
				return zugWurdeGemacht((ZugInformation) nachricht);
			else if (nachricht instanceof ZugAufforderung)
				return zugAufforderung();
			else if (nachricht instanceof KartenTausch)
				return kartenTausch();
			else if (nachricht instanceof RundenStart)
				return rundenStart((RundenStart) nachricht);
			else
				System.out.println("Nachricht " + nachricht.getClass()
				                   + " ist (noch) nicht implementiert!");

		} else {
			if (event instanceof VerbindeEvent)
				return verbinden((VerbindeEvent) event);
			else if (event instanceof AufgegebenEvent)
				return  aufgegeben();
			else if (event instanceof GezogenEvent)
				return gezogen(((GezogenEvent) event).zugEingabe);
		}
		
		return super.handle(event);
	}

	Zustand gezogen(ZugEingabe zugEingabe) {
		return keinUebergang();
	}

	Zustand aufgegeben() {
		return keinUebergang();
    }

	Zustand verbinden(VerbindeEvent event) {
		return keinUebergang();
	}

	Zustand spielVoll(EndPunkt absender, SpielVollNachricht nachricht) {
		return keinUebergang();
	}

	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println(nachricht.nachricht);

		return this;
	}

	Zustand spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {
		return keinUebergang();
	}

	Zustand beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
		return keinUebergang();
	}

	Zustand spielStarten(SpielStartNachricht nachricht) {
		return keinUebergang();
	}

	Zustand zugWurdeGemacht(ZugInformation information) {
		return keinUebergang();
	}

	Zustand zugAufforderung() {
		return keinUebergang();
	}

	Zustand kartenTausch() {
		return keinUebergang();
	}

	Zustand rundenStart(RundenStart rundenStart) {
		return keinUebergang();
	}

	Zustand keinUebergang() {
		throw new KeinUebergangException("Kein Übergang definiert in Zustand "
		                                 + this);
	}

	public void setAutomat(Automat automat) {
		this.automat = (BodesuriClient) automat;
	}
}

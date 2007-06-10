package applikation.client.zustaende;

import pd.karten.Karte;
import pd.zugsystem.ZugEingabe;
import applikation.client.SpielDaten;
import applikation.client.controller.Controller;
import applikation.events.AufgegebenEvent;
import applikation.events.FeldGewaehltEvent;
import applikation.events.GezogenEvent;
import applikation.events.HoverStartEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.events.VerbindeEvent;
import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.ChatNachricht;
import applikation.nachrichten.KartenTausch;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.SpielStartNachricht;
import applikation.nachrichten.SpielVollNachricht;
import applikation.nachrichten.ZugAufforderung;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.NetzwerkEvent;
import dienste.netzwerk.VerbindungGeschlossen;

/**
 * Spezifischer aktiver Client-Zustand
 */
public class ClientZustand extends Zustand {
	protected Controller controller;
	protected SpielDaten spielDaten;

	public Class<? extends Zustand> handle(Event event) {
		if (event instanceof NetzwerkEvent) {
			NetzwerkEvent be = (NetzwerkEvent) event;

			Brief brief = be.brief;
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof ChatNachricht)
				return chatNachricht(brief.absender, (ChatNachricht) nachricht);
			else if (nachricht instanceof SpielVollNachricht)
				return spielVoll(brief.absender, (SpielVollNachricht) nachricht);
			else if (nachricht instanceof BeitrittsBestaetigung)
				return beitrittsBestaetitigung((BeitrittsBestaetigung) nachricht);
			else if (nachricht instanceof SpielStartNachricht)
				return spielStarten((SpielStartNachricht) nachricht);
			else if (nachricht instanceof ZugInformation)
				return zugWurdeGemacht(((ZugInformation) nachricht).zug);
			else if (nachricht instanceof ZugAufforderung)
				return zugAufforderung();
			else if (nachricht instanceof KartenTausch)
				return kartenTausch(((KartenTausch) nachricht).karte);
			else if (nachricht instanceof RundenStart)
				return rundenStart((RundenStart) nachricht);
			else if (nachricht instanceof VerbindungGeschlossen)
				return verbindungGeschlossen(brief.absender);
			else
				System.out.println("Nachricht " + nachricht.getClass()
				                   + " ist (noch) nicht implementiert!");

		} else {
			if (event instanceof VerbindeEvent)
				return verbinden((VerbindeEvent) event);
			else if (event instanceof AufgegebenEvent)
				return aufgegeben();
			else if (event instanceof GezogenEvent)
				return gezogen(((GezogenEvent) event).zugEingabe);
			else if (event instanceof KarteGewaehltEvent)
				return karteGewaehlt((KarteGewaehltEvent) event);
			else if (event instanceof FeldGewaehltEvent)
				return feldGewaehlt((FeldGewaehltEvent) event);
			else if (event instanceof HoverStartEvent)
				return hoverStart((HoverStartEvent) event);
		}

		return super.handle(event);
	}



	/* GUI Handler - Verbinden */

	/* GUI Handler - Lobby */

	Class<? extends Zustand> verbinden(VerbindeEvent event) {
		return keinUebergang();
	}

	/* GUI Handler - Spiel */

	Class<? extends Zustand> gezogen(ZugEingabe zugEingabe) {
		return ignoriereEvent("gezogen");
	}

	Class<? extends Zustand> aufgegeben() {
		return ignoriereEvent("aufgegeben");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return ignoriereEvent("feldG");
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		return ignoriereEvent("karteGewaehlt");
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		return ignoriereEvent("hoverStart");
    }

	Class<? extends Zustand> verbindungGeschlossen(EndPunkt endpunkt) {
		controller.zeigeFehlermeldung("Verbindung zu Server " + endpunkt +
		                  " wurde unerwartet beendet. Client wird beendet.");
    	return EndZustand.class;

    	/* FIXME GUI Wird nicht beeendet! */
    }


	/* Netzwerk Handler */

	Class<? extends Zustand> spielVoll(EndPunkt absender, SpielVollNachricht nachricht) {
		return keinUebergang();
	}

	Class<? extends Zustand> chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println(nachricht.nachricht);

		return this.getClass();
	}

	Class<? extends Zustand> beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
		return keinUebergang();
	}

	Class<? extends Zustand> spielStarten(SpielStartNachricht nachricht) {
		return keinUebergang();
	}

	Class<? extends Zustand> zugWurdeGemacht(ZugEingabe zug) {
		return keinUebergang();
	}

	Class<? extends Zustand> zugAufforderung() {
		return keinUebergang();
	}
	Class<? extends Zustand> kartenTausch(Karte karte) {
		return keinUebergang();
	}

	Class<? extends Zustand> rundenStart(RundenStart rundenstart) {
		return keinUebergang();
	}


	/* Sonstiges */
	public void setController(Controller controller) {
	    this.controller = controller;
    }

	public void setSpielDaten(SpielDaten spielDaten) {
		this.spielDaten = spielDaten;
    }
}

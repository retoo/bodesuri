package applikation.client.zustaende;

import pd.karten.Karte;
import pd.zugsystem.ZugEingabe;
import applikation.client.BodesuriClient;
import applikation.client.controller.Controller;
import applikation.events.AufgegebenEvent;
import applikation.events.FeldGewaehltEvent;
import applikation.events.GezogenEvent;
import applikation.events.KarteGewaehltEvent;
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
import dienste.automat.zustaende.AktiverZustand;
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
public class AktiverClientZustand extends AktiverZustand {
	protected BodesuriClient automat;
	protected Controller controller;

	public Zustand handle(Event event) {
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

	Zustand kartenTausch(Karte karte) {
		return keinUebergang();
	}

	Zustand rundenStart(RundenStart rundenstart) {
		return keinUebergang();
	}

	Zustand feldGewaehlt(FeldGewaehltEvent event) {
		return keinUebergang();
	}

	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		return keinUebergang();
	}

	Zustand verbindungGeschlossen(EndPunkt endpunkt) {
		/* FIXME: controller existiert u.U. zu diesem Zeitpunkt noch gar nicht */
		controller.zeigeFehlermeldung("Verbindung zu Server " + endpunkt +
		                  " wurde unerwartet beendet. Client wird beendet.");
    	return automat.getZustand(EndZustand.class);
    }

	public void setAutomat(Automat automat) {
		this.automat = (BodesuriClient) automat;
	}
}

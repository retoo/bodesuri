package applikation.client.zugautomat.zustaende;

import applikation.client.Controller;
import applikation.client.zugautomat.ZugAutomat;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.Automat;
import dienste.automat.Event;
import dienste.automat.zustaende.AktiverZustand;
import dienste.automat.zustaende.Zustand;

public class AktiverZugZustand extends AktiverZustand {
	protected ZugAutomat automat;
	protected Controller controller;

	public Zustand handle(Event event) {
		if (event instanceof KarteGewaehltEvent)
			return karteGewaehlt((KarteGewaehltEvent) event);
		else if (event instanceof FeldGewaehltEvent)
			return feldGewaehlt((FeldGewaehltEvent) event);
		
		return super.handle(event);
	}

	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		return keinUebergang();
	}

	Zustand feldGewaehlt(FeldGewaehltEvent event) {
		return keinUebergang();
	}

	public void setAutomat(Automat automat) {
		this.automat = (ZugAutomat) automat;
	}
}

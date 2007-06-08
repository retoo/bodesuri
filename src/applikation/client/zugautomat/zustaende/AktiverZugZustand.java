package applikation.client.zugautomat.zustaende;

import applikation.client.controller.Controller;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

public class AktiverZugZustand extends Zustand {
	protected SpielDaten spielDaten;
	protected Controller controller;

	public Class<? extends Zustand> handle(Event event) {
		if (event instanceof KarteGewaehltEvent)
			return karteGewaehlt((KarteGewaehltEvent) event);
		else if (event instanceof FeldGewaehltEvent)
			return feldGewaehlt((FeldGewaehltEvent) event);

		return super.handle(event);
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		return keinUebergang();
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return keinUebergang();
	}

	public void setController(Controller controller) {
		this.controller = controller;
    }

	public void setspielDaten(SpielDaten spielDaten) {
	    this.spielDaten = spielDaten;
    }
}

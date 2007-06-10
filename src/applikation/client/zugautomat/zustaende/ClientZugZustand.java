package applikation.client.zugautomat.zustaende;

import applikation.client.controller.Controller;
import applikation.client.zugautomat.SpielDaten;
import applikation.events.FeldGewaehltEvent;
import applikation.events.HoverStartEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

public class ClientZugZustand extends Zustand {
	protected SpielDaten spielDaten;
	protected Controller controller;

	public Class<? extends Zustand> handle(Event event) {
		if (event instanceof KarteGewaehltEvent)
			return karteGewaehlt((KarteGewaehltEvent) event);
		else if (event instanceof FeldGewaehltEvent)
			return feldGewaehlt((FeldGewaehltEvent) event);
		else if (event instanceof HoverStartEvent)
			return hoverStart((HoverStartEvent) event);

		return super.handle(event);
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		return ignoriereEvent("hoverStart");
    }

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		return ignoriereEvent("karteGewaehlt");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return ignoriereEvent("feldGewaehlt");
	}

	public void setController(Controller controller) {
		this.controller = controller;
    }

	public void setspielDaten(SpielDaten spielDaten) {
	    this.spielDaten = spielDaten;
    }
}

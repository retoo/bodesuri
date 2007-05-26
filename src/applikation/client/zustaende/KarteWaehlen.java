package applikation.client.zustaende;

import applikation.client.events.KarteGewaehltEvent;
import applikation.server.nachrichten.ChatNachricht;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

public class KarteWaehlen extends AktiverClientZustand {
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		automat.karte = event.karte;
		return automat.getZustand(Ziehen.class);
	}
}

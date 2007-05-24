package applikation.client.zustaende;

import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.ZugAufforderung;
import dienste.automat.Zustand;
import dienste.netzwerk.EndPunkt;

public class NichtAmZug extends AktiverClientZustand {
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	//TODO: Brauchen wir die Nachricht übrerhaupt?
	Zustand zugAufforderung(ZugAufforderung aufforderung) {
		return automat.getZustand(AmZug.class);
	}
}

package applikation.client.zustaende;

import applikation.server.nachrichten.ChatNachricht;
import dienste.automat.Zustand;
import dienste.netzwerk.EndPunkt;

// TODO ist nun das 3. Spiel. Ein anderer Name wäre gut.
public class Spiel extends AktiverClientZustand {
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ":");
		System.out.println(nachricht);
		return this;
	}
}

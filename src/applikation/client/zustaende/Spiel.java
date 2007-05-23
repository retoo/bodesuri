package applikation.client.zustaende;

import applikation.server.nachrichten.ChatNachricht;
import dienste.automat.State;
import dienste.netzwerk.EndPunkt;

// TODO ist nun das 3. Spiel. Ein anderer Name wäre gut.
public class Spiel extends ActiveClientState {
	State chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ":");
		System.out.println(nachricht);
		return this;
	}
}

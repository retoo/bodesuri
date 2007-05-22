package applikation.client.states;

import applikation.server.nachrichten.ChatNachricht;
import dienste.netzwerk.EndPunkt;
import dienste.statemachine.State;

// TODO ist nun das 3. Spiel. Ein anderer Name wäre gut.
public class Spiel extends ActiveClientState {
	State chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ":");
		System.out.println(nachricht);
		return this;
	}
}

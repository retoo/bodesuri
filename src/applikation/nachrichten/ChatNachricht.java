package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die eine Chatnachricht beinhaltet
 */
public class ChatNachricht extends Nachricht {
	public final String text;
	public final String sender;

	public ChatNachricht(String sender, String nachricht) {
		this.text = nachricht;
		this.sender = sender;
	}
}

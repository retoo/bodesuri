package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Client dem Server vom Benutzer eingegebenen Chat-Text
 * übermittelt und der Server den Clients den Chat-Text zurückgibt.
 */
public class ChatNachricht extends Nachricht {
	public final String text;
	public final String sender;

	public ChatNachricht(String sender, String nachricht) {
		this.text = nachricht;
		this.sender = sender;
	}
}

package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die eine Chatnachricht beinhaltet
 */
public class ChatNachricht extends Nachricht {
	/**
	 * Chat-Nachricht
	 */
	public final String nachricht;

	/**
	 * @param nachricht
	 */
	public ChatNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	public String toString() {
		return nachricht;
	}
}

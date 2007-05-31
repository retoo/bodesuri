package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die eine Chatnachricht beinhaltet
 */
public class ChatNachricht extends Nachricht {
	private static final long serialVersionUID = 564278019291322550L;
	
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

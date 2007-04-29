package spielplatz.hilfsklassen;


public class ChatNachricht extends Nachricht {
	private static final long serialVersionUID = 564278019291322550L;
	
	String nachricht;
	
	public ChatNachricht(String nachricht) {
		this.nachricht = nachricht;	
	}
	
	public String toString() {
		return nachricht;
	}

}

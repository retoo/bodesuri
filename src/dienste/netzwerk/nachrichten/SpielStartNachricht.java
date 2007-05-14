package dienste.netzwerk.nachrichten;


public class SpielStartNachricht extends Nachricht {
	private static final long serialVersionUID = 1L;
	
	final public String[] spieler;
	
	public SpielStartNachricht(String[] spieler) {
		this.spieler = spieler;
	}
}

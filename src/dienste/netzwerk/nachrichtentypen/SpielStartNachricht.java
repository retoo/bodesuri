package dienste.netzwerk.nachrichtentypen;



public class SpielStartNachricht extends Nachricht {
	private static final long serialVersionUID = 1L;
	public String[] spieler;
	
	public SpielStartNachricht(String[] spieler) {
		this.spieler = spieler;
	}

	@Deprecated
	public SpielStartNachricht() {
	    // TODO Auto-generated constructor stub
    }
}

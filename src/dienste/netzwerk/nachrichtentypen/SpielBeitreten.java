package dienste.netzwerk.nachrichtentypen;



public class SpielBeitreten extends Nachricht {
	private static final long serialVersionUID = 1L;
    public String spielerName;

	public SpielBeitreten(String spielerName) {
	    this.spielerName = spielerName;
    }    
}

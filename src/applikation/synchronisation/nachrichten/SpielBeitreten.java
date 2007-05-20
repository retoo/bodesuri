package applikation.synchronisation.nachrichten;

import dienste.netzwerk.Nachricht;

public class SpielBeitreten extends Nachricht {
	private static final long serialVersionUID = 1L;
	
    final public String spielerName;

	public SpielBeitreten(String spielerName) {
	    this.spielerName = spielerName;
    }    
}

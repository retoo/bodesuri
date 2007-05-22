package applikation.server.nachrichten;

import dienste.netzwerk.Nachricht;

public class BeitrittsBestaetigung extends Nachricht {
    private static final long serialVersionUID = 1L;

    public final String[] spielerNamen;
    
    public BeitrittsBestaetigung(String[] spielerNamen ) {
    	this.spielerNamen = spielerNamen;
    }  
}

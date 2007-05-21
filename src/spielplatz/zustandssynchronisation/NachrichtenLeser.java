package spielplatz.zustandssynchronisation;

import dienste.netzwerk.Briefkasten;

public class NachrichtenLeser implements EventSource {
	private Briefkasten briefkasten;

	public NachrichtenLeser(Briefkasten briefkasten) {
		this.briefkasten = briefkasten;
		
	}
	
	public Event getEevent() {
	    return new BodesuriEvent(briefkasten.getBrief());
    }
}
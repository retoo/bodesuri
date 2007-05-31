package applikation.events;

import pd.brett.Feld;
import dienste.automat.Event;

public class FeldGewaehltEvent extends Event {
	public final Feld feld;
	
	public FeldGewaehltEvent(Feld feld) {
		this.feld = feld;
	}
}

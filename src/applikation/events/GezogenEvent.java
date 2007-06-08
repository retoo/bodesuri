package applikation.events;

import pd.zugsystem.ZugEingabe;
import dienste.eventqueue.Event;

public class GezogenEvent extends Event {
	public final ZugEingabe zugEingabe;
	
	public GezogenEvent (ZugEingabe zugEingabe) {
		this.zugEingabe = zugEingabe;
	}
}

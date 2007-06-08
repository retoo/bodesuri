package applikation.client;

import applikation.client.zugautomat.ZugAutomat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunkt;

public class SpielDaten {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public ZugAutomat zugAutomat;

}

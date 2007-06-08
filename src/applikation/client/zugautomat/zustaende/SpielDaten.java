package applikation.client.zugautomat.zustaende;

import pd.brett.Feld;
import pd.karten.Karte;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunkt;

public class SpielDaten {
	public EventQueue eventQueueBodesuriClient;

	public EndPunkt endpunkt;

	public Karte karte;
	public Feld start;
	public Feld ziel;
}

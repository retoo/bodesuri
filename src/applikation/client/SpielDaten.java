package applikation.client;

import pd.Spiel;
import pd.SpielThreads;
import applikation.client.zugautomat.ZugAutomat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunkt;
import dienste.serialisierung.SerialisierungsKontext;

public class SpielDaten implements SerialisierungsKontext {
	public EventQueue queue;
	public EndPunkt endpunkt;
	public ZugAutomat zugAutomat;
	public Spiel spiel;
	//TODO: Wird nirgends gebraucht???
	public int anzSpieler;

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, spiel);
	}
}

package applikation.client;

import java.util.HashMap;
import java.util.Map;

import pd.Spiel;
import pd.SpielThreads;
import pd.spieler.Spieler;
import applikation.client.zugautomat.ZugAutomat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunkt;
import dienste.serialisierung.SerialisierungsKontext;

public class SpielDaten implements SerialisierungsKontext {
	public EventQueue queue;
	public EndPunkt endpunkt;
	public ZugAutomat zugAutomat;
	public Spiel spiel = new Spiel();
	public String spielerName;
	public Spieler spielerIch;
	public Map<Spieler, applikation.client.controller.Spieler> spielers =
		new HashMap<Spieler, applikation.client.controller.Spieler>();
	public applikation.client.controller.Spieler aktuellerSpieler;

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, spiel);
	}
}

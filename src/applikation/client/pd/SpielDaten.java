package applikation.client.pd;

import java.util.HashMap;
import java.util.Map;

import pd.Spiel;
import pd.SpielThreads;
import pd.spieler.Spieler;
import applikation.client.zugautomat.ZugAutomat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.serialisierung.SerialisierungsKontext;

public class SpielDaten implements SerialisierungsKontext {
	public EventQueue queue;
	public EndPunktInterface endpunkt;
	public ZugAutomat zugAutomat;
	
	public Spiel spiel = new Spiel();
	public String spielerName;
	public Spieler spielerIch;
	
	public Map<Spieler, applikation.client.pd.Spieler> spielers =
		new HashMap<Spieler, applikation.client.pd.Spieler>();
	public applikation.client.pd.Spieler aktuellerSpieler;

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, spiel);
	}
}

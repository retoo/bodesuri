package applikation.client.pd;

import pd.SpielThreads;
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
	
	public applikation.client.pd.Spieler aktuellerSpieler;

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, spiel.getSpiel());
	}
}

package applikation.client.zugautomat;

import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;

public class SpielDaten {
	public EventQueue eventQueueBodesuriClient;

	public EndPunktInterface endpunkt;

	public Spieler spielerIch;
	public Karte karte;
	public Feld start;
	public Feld ziel;
}

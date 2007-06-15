package applikation.client.zugautomat;

import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;

public class SpielDaten {
	public EventQueue eventQueueBodesuriClient;
	public EndPunktInterface endpunkt;

	public Spieler spielerIch;
	public Karte karte;
	public Feld start;
	public Feld ziel;
	public Spiel spiel;
}

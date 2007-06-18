package applikation.client.zugautomat.pd;

import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.pd.Spiel;
import dienste.netzwerk.EndPunktInterface;

public class SpielDaten {
	public EndPunktInterface endpunkt;

	public Karte karte;
	public Feld start;
	public Feld ziel;
	public Spiel spiel;
}

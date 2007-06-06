package applikation.client.zugautomat;

import pd.brett.Feld;
import pd.karten.Karte;
import applikation.client.Controller;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.client.zugautomat.zustaende.ZugValidieren;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.EndPunkt;

public class ZugAutomat extends Automat {
	public EventQueue eventQueueBodesuriClient;

	public Controller controller;
	public EndPunkt endpunkt;

	public Karte karte;
	public Feld start;
	public Feld ziel;

	public ZugAutomat(Controller controller, EventQueue eventQueueBodesuriClient) {
		this.controller = controller;
		this.eventQueueBodesuriClient = eventQueueBodesuriClient;

		registriere(new KarteWaehlen());
		registriere(new StartWaehlen());
		registriere(new EndeWaehlen());
		registriere(new ZugValidieren());

		setStart(KarteWaehlen.class);
	}

	public String toString() {
		return "Zug-Automat";
	}
}

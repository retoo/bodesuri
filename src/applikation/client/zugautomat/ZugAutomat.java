package applikation.client.zugautomat;

import pd.brett.Feld;
import pd.karten.Karte;
import applikation.client.controller.Controller;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.client.zugautomat.zustaende.ZugValidieren;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunkt;

public class ZugAutomat extends Automat {
	public EventQueue eventQueueBodesuriClient;

	public EndPunkt endpunkt;

	public Karte karte;
	public Feld start;
	public Feld ziel;

	public ZugAutomat(Controller controller, EventQueue eventQueueBodesuriClient) {
		this.eventQueueBodesuriClient = eventQueueBodesuriClient;

		registriere(new KarteWaehlen(controller));
		registriere(new StartWaehlen(controller));
		registriere(new EndeWaehlen(controller));
		registriere(new ZugValidieren(controller));

		setStart(KarteWaehlen.class);
	}

	public String toString() {
		return "Zug-Automat";
	}
}

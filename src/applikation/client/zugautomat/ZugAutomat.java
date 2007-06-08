package applikation.client.zugautomat;

import applikation.client.controller.Controller;
import applikation.client.zugautomat.zustaende.AktiverZugZustand;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.SpielDaten;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.client.zugautomat.zustaende.ZugValidieren;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

public class ZugAutomat extends Automat {
	private Controller controller;
	private SpielDaten spielDaten;

	public ZugAutomat(Controller controller, EventQueue eventQueueBodesuriClient) {
		this.controller = controller;
		spielDaten = new SpielDaten();
		spielDaten.eventQueueBodesuriClient = eventQueueBodesuriClient;

		registriere(new KarteWaehlen(controller));
		registriere(new StartWaehlen(controller));
		registriere(new EndeWaehlen(controller));
		registriere(new ZugValidieren(controller));

		setStart(KarteWaehlen.class);
	}

	public void registriere(AktiverZugZustand zustand) {
		zustand.setController(controller);
		zustand.setspielDaten(spielDaten);
	    super.registriere(zustand);
	}

	public String toString() {
		return "Zug-Automat";
	}
}

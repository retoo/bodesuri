package applikation.client.zugautomat;

import applikation.client.controller.Controller;
import applikation.client.zugautomat.zustaende.ClientZugZustand;
import applikation.client.zugautomat.zustaende.EndeWaehlen;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
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

		setStart(KarteWaehlen.class);
	}

	public void registriere(ClientZugZustand zustand) {
		zustand.setController(controller);
		zustand.setspielDaten(spielDaten);
	    super.registriere(zustand);
	}

	public String toString() {
		return "Zug-Automat";
	}
}

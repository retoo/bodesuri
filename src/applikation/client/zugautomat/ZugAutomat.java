package applikation.client.zugautomat;

import applikation.client.controller.Controller;
import applikation.client.pd.Spiel;
import applikation.client.zugautomat.pd.SpielDaten;
import applikation.client.zugautomat.zustaende.ClientZugZustand;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.StartWaehlen;
import applikation.client.zugautomat.zustaende.ZielWaehlen;
import applikation.client.zugautomat.zustaende.ZugValidieren;
import applikation.client.zugautomat.zustaende.ZugautomatAbschluss;
import dienste.automat.Automat;

public class ZugAutomat extends Automat {
	private Controller controller;
	private SpielDaten spielDaten;

	public ZugAutomat(Controller controller, Spiel spiel) {
		this.controller = controller;
		spielDaten = new SpielDaten(spiel);

		registriere(new KarteWaehlen(controller));
		registriere(new StartWaehlen(controller));
		registriere(new ZielWaehlen(controller));
		registriere(new ZugValidieren());
		registriere(new ZugautomatAbschluss());

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

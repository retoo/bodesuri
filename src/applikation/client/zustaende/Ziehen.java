package applikation.client.zustaende;

import applikation.client.events.ZugEingegebenEvent;
import applikation.zugentgegennahme.ZugEntgegennahme;
import dienste.automat.Zustand;

public class Ziehen extends AktiverClientZustand {
	protected void init() {
		automat.zugentgegennahme = new ZugEntgegennahme(automat.queue);
	}

	Zustand zugEingegeben(ZugEingegebenEvent event) {
		automat.bewegung = event.bewegung;

		return automat.getZustand(ZugValidieren.class);
	}
}

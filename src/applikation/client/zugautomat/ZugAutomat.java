package applikation.client.zugautomat;

import pd.karten.Karte;
import pd.spieler.Spieler;
import applikation.client.zugautomat.zustaende.KarteWaehlen;
import applikation.client.zugautomat.zustaende.Ziehen;
import applikation.zugentgegennahme.ZugEntgegennahme;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

public class ZugAutomat extends Automat {
	public EventQueue queue;

	public Spieler spielerIch;
	public EndPunkt endpunkt;

	public Karte karte;
	public ZugEntgegennahme zugentgegennahme;

	public ZugAutomat(Spieler spielerIch, EndPunkt endpunkt) {
		this.spielerIch = spielerIch;
		this.endpunkt = endpunkt;

		registriere(new KarteWaehlen());
		registriere(new Ziehen());

		setStart(KarteWaehlen.class);

		this.queue = new EventQueue();
		setEventQuelle(queue);
	}

	public boolean isZustand(Class<? extends Zustand> klass) {
		return getAktuellerZustand() == getZustand(klass);
	}
}

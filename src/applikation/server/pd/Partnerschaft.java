package applikation.server.pd;

import java.util.Vector;

public class Partnerschaft {
	Vector<Spieler> partner;

	public Partnerschaft(Spieler spielerA, Spieler spielerB) {
		partner = new Vector<Spieler>();
		partner.add(spielerA);
		partner.add(spielerB);
	}

	public boolean istFertig() {
		for (Spieler s : partner) {
			if (!s.istFertig()) {
				return false;
			}
		}

		return true;
	}

}

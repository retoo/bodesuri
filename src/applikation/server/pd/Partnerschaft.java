package applikation.server.pd;

import java.util.List;
import java.util.Vector;

import applikation.geteiltes.SpielerInfo;

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

	public PartnerSchaftsInfo getPartnerSchaftInfo() {
		List<SpielerInfo> spieler = new Vector<SpielerInfo>();

		for (Spieler s : partner) {
			spieler.add(s.getSpielerInfo());
		}

		return new PartnerSchaftsInfo(spieler);
	}
}

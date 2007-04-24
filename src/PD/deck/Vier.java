package PD.deck;

import PD.regelsystem.VorwaertsRegel;

public class Vier extends Karte {
	public Vier() {
		regeln.add(new VorwaertsRegel(4));
	}
}

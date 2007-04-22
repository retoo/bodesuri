package PD.Deck;

import PD.Regelsystem.VorwaertsRegel;

public class Vier extends Karte {
	public Vier() {
		regeln.add(new VorwaertsRegel(4));
	}
}

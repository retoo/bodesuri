package PD.Deck;

import java.util.Vector;

import PD.Regelsystem.Regel;

public class Karte {
	private int wert;
	protected Vector<Regel> regeln = new Vector<Regel>();

	public void setWert(int wert) {
		this.wert = wert;
	}

	public int getWert() {
		return wert;
	}

	public void setKartenFarbe(KartenFarbe kartenFarbe) {

	}

	public KartenFarbe getKartenFarbe() {
		return null;
	}

	public Vector<Regel> getRegeln() {
    	return regeln;
    }
}

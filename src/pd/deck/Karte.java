package pd.deck;

import pd.regelsystem.Regel;

public class Karte {
	private int wert;
	private Regel regel;
	
	public String toString() {
		return this.getClass().getSimpleName();
	}

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

	public Regel getRegel() {
    	return regel;
    }
	
	protected void setRegel(Regel regel) {
		this.regel = regel;
	}
}

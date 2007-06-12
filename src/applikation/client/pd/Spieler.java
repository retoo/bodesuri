package applikation.client.pd;

import java.util.Observable;


public class Spieler extends Observable {
	private Boolean amZug;
	public pd.spieler.Spieler spieler;

	public Spieler(pd.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.amZug = false;
	}

	public Boolean getAmZug() {
		return amZug;
	}

	public void setAmZug(Boolean amZug) {
		this.amZug = amZug;
		setChanged();
		notifyObservers(amZug);
	}

	public pd.spieler.Spieler getSpieler() {
    	return spieler;
    }
}

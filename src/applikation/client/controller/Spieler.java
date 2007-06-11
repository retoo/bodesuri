package applikation.client.controller;

import java.util.Observable;

public class Spieler extends Observable {
	private Boolean amZug;
	private SpielerFarbe farbe;
	private pd.spieler.Spieler spieler;

	public Spieler(pd.spieler.Spieler spieler, SpielerFarbe farbe) {
		this.farbe = farbe;
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

	public SpielerFarbe getFarbe() {
    	return farbe;
    }

	public pd.spieler.Spieler getSpieler() {
    	return spieler;
    }
}

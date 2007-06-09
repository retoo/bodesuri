package applikation.client.controller;

import java.util.Observable;

public class Spieler extends Observable {
	private Boolean amZug;
	private SpielerFarbe farbe;

	public Spieler(float rot, float gruen, float blau) {
		this.amZug = false;
		this.farbe = new SpielerFarbe(rot, gruen, blau);
	}

	public Boolean getAmZug() {
		return amZug;
	}

	public void setAmZug(Boolean amZug) {
		this.amZug = amZug;
		setChanged();
		notify();
	}

	public SpielerFarbe getFarbe() {
    	return farbe;
    }
}

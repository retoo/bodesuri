package applikation.client;

import java.util.Observable;

public class Spieler extends Observable {
	private Boolean amZug;

	public Spieler() {
		this.amZug = false;
	}

	public Boolean getAmZug() {
		return amZug;
	}

	public void setAmZug(Boolean amZug) {
		this.amZug = amZug;
		setChanged();
		notify();
	}
}

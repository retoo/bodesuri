package applikation.client.pd;

import dienste.observer.ObservableList;

public class Karten extends ObservableList<Karte> {
	private boolean aktiv;

	public Karten() {
		this.aktiv = false;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
		this.setChanged();
		notifyObservers();
	}

	public boolean getAktiv() {
		return aktiv;
	}
}

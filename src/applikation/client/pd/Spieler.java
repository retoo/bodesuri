package applikation.client.pd;

import java.util.Observable;

import pd.karten.Karte;

import dienste.observer.ObservableList;

public class Spieler extends Observable {
	private Boolean amZug;
	public pd.spieler.Spieler spieler;

	private ObservableList<Karte> karten = new ObservableList<Karte>();

	public Spieler(pd.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.amZug = false;
	}

	public boolean kannZiehen() {
		return spieler.kannZiehen();
	}

	public ObservableList<Karte> getKarten() {
		return karten;
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

package applikation.client.pd;

import java.util.Observable;
import java.util.Observer;

import dienste.observer.ObservableList;

public class Karten extends Observable implements Observer {
	private boolean aktiv;
	private ObservableList<pd.karten.Karte> pdKarten;
	private ObservableList<Karte> appKarten;

	public Karten(ObservableList<pd.karten.Karte> karten) {
		this.pdKarten = karten;
		this.aktiv = false;
		this.appKarten = new ObservableList<Karte>();
		appKarten.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
		setChanged();
		notifyObservers();
	}

	public boolean getAktiv() {
		return aktiv;
	}

	public Karte get(int i) {
		return appKarten.get(i);
    }

	public int size() {
		return appKarten.size();
	}

	public void add(Karte karte) {
		pdKarten.add(karte.getKarte());
	    appKarten.add(karte);
    }

	public void remove(Karte karte) {
		pdKarten.remove(karte.getKarte());
		appKarten.remove(karte);
    }

	public void clear() {
		pdKarten.clear();
		appKarten.clear();
	}
}

package applikation.client.pd;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import dienste.observer.ObservableList;

/**
 * Speichert Karten sowohl aus der PD als auch aus der Applikaktionsschicht.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob die Kartenauswahl
 * aktiviert ist.
 */
public class Karten extends Observable implements Observer, Iterable<Karte> {
	private boolean aktiv;
	private ObservableList<pd.regelsystem.karten.Karte> pdKarten;
	private ObservableList<Karte> appKarten;

	public Karten(ObservableList<pd.regelsystem.karten.Karte> karten) {
		this.pdKarten = karten;
		this.aktiv = false;
		this.appKarten = new ObservableList<Karte>();
		appKarten.addObserver(this);
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

	public Iterator<Karte> iterator() {
		return appKarten.iterator();
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
		for (Karte karte : appKarten) {
			if (karte.istAusgewaehlt()) {
				karte.setAusgewaehlt(false);
			}
		}
		pdKarten.clear();
		appKarten.clear();
	}
	
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}
}

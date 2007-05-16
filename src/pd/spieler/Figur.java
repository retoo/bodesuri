package pd.spieler;

import java.util.Observable;

import pd.brett.Feld;

public class Figur extends Observable {
	private Spieler spieler;

	public Figur(Spieler spieler) {
		this.spieler = spieler;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public void wurdeBewegt(Feld ziel) {
		setChanged();
		notifyObservers(ziel);
	}
}

package applikation.client.pd;

import java.util.Observable;

import pd.karten.Joker;
import pd.karten.KartenFarbe;

public class Karte extends Observable {
	private boolean ausgewaehlt;
	private pd.karten.Karte karte;

	public Karte(pd.karten.Karte karte) {
		this.karte = karte;
		ausgewaehlt = false;
	}

	public boolean istAusgewaehlt() {
		return ausgewaehlt;
	}

	public void setAusgewaehlt(boolean ausgewaehlt) {
		this.ausgewaehlt = ausgewaehlt;
		setChanged();
		notifyObservers(ausgewaehlt);
	}

	public KartenFarbe getKartenFarbe() {
	    return karte.getKartenFarbe();
    }

	public String getRegelBeschreibung() {
		return karte.getRegelBeschreibung();
	}

	public pd.karten.Karte getKarte() {
		return karte;
	}

	public String getIconName() {
		if (karte instanceof Joker) {
			return "joker";
		} else {
			String name = karte.getClass().getSimpleName() + "_" + karte.getKartenFarbe();
			return name.toLowerCase();
		}
	}
	
	public String toString() {
		return karte.toString();
	}
}

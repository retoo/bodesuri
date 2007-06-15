package applikation.client.pd;

import java.util.Observable;

import pd.karten.Joker;
import pd.karten.KartenFarbe;

public class Karte extends Observable {
	private Boolean ausgewaehlt;
	private pd.karten.Karte karte;

	public Karte(pd.karten.Karte karte) {
		this.karte = karte;
	}

	public Boolean getAusgewaehlt() {
		return ausgewaehlt;
	}

	public void setAusgewaehlt(Boolean ausgewaehlt) {
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

	public String getName() {
		/* TODO: könnte man noch sauberer in die pd schieben (-reto) */
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

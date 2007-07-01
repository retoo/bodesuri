package applikation.client.pd;

import java.util.Observable;

import pd.regelsystem.Regel;
import pd.regelsystem.karten.Joker;
import pd.regelsystem.karten.KartenFarbe;

/**
 * Dekoriert eine Karte aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob die Karte ausgewählt ist.
 */
public class Karte extends Observable {
	private pd.regelsystem.karten.Karte karte;
	
	private boolean ausgewaehlt;

	public Karte(pd.regelsystem.karten.Karte karte) {
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

	public String getIconName() {
		if (karte instanceof Joker) {
			return "joker";
		} else {
			String name = karte.getClass().getSimpleName() + "_" + karte.getKartenFarbe();
			return name.toLowerCase();
		}
	}
	
	public KartenFarbe getKartenFarbe() {
		return karte.getKartenFarbe();
	}
	
	public String getRegelBeschreibung() {
		return karte.getRegelBeschreibung();
	}
	
	public pd.regelsystem.karten.Karte getKarte() {
		return karte;
	}

	public Regel getRegel() {
		return karte.getRegel();
    }
	
	public String toString() {
		return karte.toString();
	}
}

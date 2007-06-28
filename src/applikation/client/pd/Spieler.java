package applikation.client.pd;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;
import pd.zugsystem.ZugEingabe;

/**
 * Dekoriert einen Spieler aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob der Spieler am Zug ist
 * und ob er Aufgegeben hat.
 * Verfügt ausserdem über eigene Karten welche die Karten aus der PD dekorieren.
 */
public class Spieler extends Observable implements Observer {
	private pd.spieler.Spieler spieler;
	private Karten karten;

	private boolean amZug;
	private boolean hatAufgegeben;

	public Spieler(pd.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.karten = new Karten(spieler.getKarten());
		this.amZug = false;
		this.hatAufgegeben = false;
		spieler.addObserver(this);
	}

	public boolean kannZiehen() {
		return spieler.kannZiehen();
	}

	public void setAmZug(boolean amZug) {
		this.amZug = amZug;
		setChanged();
		notifyObservers(amZug);
	}

	public boolean hatAufgegeben() {
		return hatAufgegeben;
	}

	public void setHatAufgebeben(boolean hatAufgebeben) {
		this.hatAufgegeben = hatAufgebeben;
		setChanged();
		notifyObservers();
	}

	public Karten getKarten() {
		return karten;
	}

	public pd.spieler.Spieler getSpieler() {
		return spieler;
	}

	public String getName() {
		return spieler.getName();
	}

	public List<Figur> getFiguren() {
		return spieler.getFiguren();
	}

	public SpielerFarbe getFarbe() {
		return spieler.getFarbe();
	}

	public boolean amZug() {
		return amZug;
	}

	public void setPartner(pd.spieler.Spieler partner) {
		spieler.setPartner(partner);
	}

	public pd.spieler.Spieler getPartner() {
		return spieler.getPartner();
	}

	public List<ZugEingabe> getMoeglicheZuege() {
		return spieler.getMoeglicheZuege();
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

	public String toString() {
		return spieler.toString();
	}
}

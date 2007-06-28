package applikation.client.pd;

import java.util.Observable;
import java.util.Observer;

import pd.spieler.Figur;

/**
 * Dekoriert ein Feld aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob das Feld ausgewählt ist,
 * das Feld gehovert ist, ein Geist auf dem Feld ist oder das Feld Teil eines
 * markierten Weges ist.
 */
public class Feld extends Observable implements Observer {
	private pd.brett.Feld feld;

	private boolean ausgewaehlt;
	private boolean hover;
	private boolean geist;
	private boolean weg;

	public Feld(pd.brett.Feld feld) {
		this.feld = feld;

		this.hover = false;
		this.ausgewaehlt = false;
		this.geist = false;
		this.weg = false;

		feld.addObserver(this);
	}

	public pd.brett.Feld getFeld() {
		return feld;
	}

	public Figur getFigur() {
		return feld.getFigur();
	}

	public Object getNummer() {
		return feld.getNummer();
	}

	public boolean istBesetzt() {
		return feld.istBesetzt();
	}

	public boolean istNormal() {
		return feld.istNormal();
	}

	public boolean istLager() {
		return feld.istLager();
	}

	/* Feld-Zustände */

	public void setAusgewaehlt(boolean zustand) {
		this.ausgewaehlt = zustand;
		setChanged();
		notifyObservers(zustand);
	}

	public void setHover(boolean zustand) {
		this.hover = zustand;
		setChanged();
		notifyObservers(ausgewaehlt);
	}

	public void setWeg(boolean zustand) {
		this.weg = zustand;
		setChanged();
		notifyObservers();
	}

	public void setGeist(boolean zustand) {
		this.geist = zustand;
		setChanged();
		notifyObservers();
	}

	public boolean istAusgewaehlt() {
		return ausgewaehlt;
	}

	public boolean istHover() {
		return hover;
	}

	public boolean istWeg() {
		return weg;
	}

	public boolean istGeist() {
		return geist;
	}
	
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
}

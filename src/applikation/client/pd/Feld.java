package applikation.client.pd;

import java.util.Observable;
import java.util.Observer;

import pd.spieler.Figur;

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

	/* TODO: Philippe: Brauchen wir das wirklich noch ? (-reto) */
	public void versetzeFigurAuf(Feld ziel) {
		feld.versetzeFigurAuf(ziel.getFeld());
	}

	/* TODO: Reto: Rename nach 'istAuswgewaehlt' */
	public boolean getAusgewaehlt() {
		return ausgewaehlt;
	}

	/* TODO: Reto: Rename nach 'istHover' */
	public boolean getHover() {
		return hover;
	}

	public void setAusgewaehlt(boolean ausgewaehlt) {
		this.ausgewaehlt = ausgewaehlt;
		setChanged();
		notifyObservers(ausgewaehlt);
	}

	public void setHover(boolean hover) {
		this.hover = hover;
		setChanged();
		notifyObservers(ausgewaehlt);
    }

	public Figur getFigur() {
		return feld.getFigur();
	}

	public Object getNummer() {
		return feld.getNummer();
	}

	public pd.brett.Feld getFeld() {
		return feld;
	}

	public boolean istBesetzt() {
		return feld.istBesetzt();
	}

	public boolean istNormal() {
		return feld.istNormal();
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
	public boolean istLager() {
	    return feld.istLager();
    }
	public void setGeist(boolean b) {
	    this.geist = true;
	    setChanged();
	    notifyObservers();

    }
	public void setWeg(boolean zustand) {
		this.weg = zustand;
		setChanged();
		notifyObservers();
	}

    public boolean istGeist() {
    	return geist;
    }

    public boolean istWeg() {
    	return weg;
    }
}

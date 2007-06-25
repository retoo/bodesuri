package applikation.client.pd;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;
import pd.zugsystem.ZugEingabe;

public class Spieler extends Observable implements Observer {
	private boolean amZug;
	private boolean hatAufgegeben;
	public pd.spieler.Spieler spieler;

	private Karten karten;

	public Spieler(pd.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.amZug = false;
		this.hatAufgegeben = false;
		this.karten = new Karten(spieler.getKarten());
		spieler.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

	public boolean kannZiehen() {
		return spieler.kannZiehen();
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

	public boolean getAmZug() {
		return amZug;
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

	public String toString() {
		return spieler.toString();
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
}

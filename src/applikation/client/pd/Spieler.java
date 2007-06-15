package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Observable;

import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;
import dienste.observer.ObservableList;

public class Spieler extends Observable {
	private static IdentityHashMap<pd.spieler.Spieler, Spieler> spielerRegister = new IdentityHashMap<pd.spieler.Spieler, Spieler>();

	private Boolean amZug;
	public pd.spieler.Spieler spieler;

	private ObservableList<Karte> karten;

	public Spieler(pd.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.amZug = false;
		this.karten = new ObservableList<Karte>();

		spielerRegister.put(spieler, this);
	}

	public static Spieler findeSpieler(pd.spieler.Spieler spieler) {
		Spieler s = spielerRegister.get(spieler);

		if (s == null) {
			throw new RuntimeException("Kann app.Spieler für den Spieler " + spieler + " nicht finden!");
		}
		return s;
	}

	public boolean kannZiehen() {
		return spieler.kannZiehen();
	}

	public ObservableList<Karte> getKarten() {
		return karten;
	}

	public Boolean getAmZug() {
		return amZug;
	}

	public void setAmZug(Boolean amZug) {
		this.amZug = amZug;
		setChanged();
		notifyObservers(amZug);
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
}

package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Observable;

import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;

public class Spieler extends Observable {
	/* FIXME: reto unstatisch (-reto) */
	private static IdentityHashMap<pd.spieler.Spieler, Spieler> spielerRegister = new IdentityHashMap<pd.spieler.Spieler, Spieler>();

	private Boolean amZug;
	public pd.spieler.Spieler spieler;

	private Karten karten;

	public Spieler(pd.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.amZug = false;
		this.karten = new Karten();

		spielerRegister.put(spieler, this);
	}

	/* FIXME: reto unstatisch (-reto) */
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

	public Karten getKarten() {
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

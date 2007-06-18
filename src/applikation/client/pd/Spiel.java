package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import pd.zugsystem.ZugEingabe;

public class Spiel extends Observable {
	private pd.Spiel spiel;

	private Brett brett;
	private Vector<Spieler> spieler;
	
	private String hinweis;
	private ZugEingabe letzterZug;
	private IdentityHashMap<pd.spieler.Spieler, Spieler> spielerRegister;
	private Chat chat;

	public Spiel() {
		spiel = new pd.Spiel();
		this.spielerRegister = new IdentityHashMap<pd.spieler.Spieler, Spieler>();

		spieler = new Vector<Spieler>();
		brett = new Brett(spiel.getBrett());
		chat = new Chat();
	}

	// TODO: Heisst bei Reto neuerSpieler()
	public Spieler fuegeHinzu(String spielerName) {
		pd.spieler.Spieler spielerPD = spiel.fuegeHinzu(spielerName);

		Spieler neuerSpieler = new Spieler(spielerPD);

		spieler.add(neuerSpieler);
		spielerRegister.put(spielerPD, neuerSpieler);

		return neuerSpieler;
	}

	public Spieler findeSpieler(pd.spieler.Spieler spieler) {
		Spieler s = spielerRegister.get(spieler);

		if (s == null) {
			throw new RuntimeException("Kann app.Spieler für den Spieler " + spieler + " nicht finden!");
		}
		return s;
	}


	public Brett getBrett() {
		return brett;
	}

	public List<Spieler> getSpieler() {
		return spieler;
	}

	public pd.Spiel getSpiel() {
		return spiel;
	}

	public String getHinweis() {
		return hinweis;
	}

	public void setHinweis(String hinweis) {
		this.hinweis = hinweis;
		setChanged();
		notifyObservers();
	}

	public Chat getChat() {
	    return chat;
    }

	public ZugEingabe getLetzterZug() {
    	return letzterZug;
    }

	public void setLetzterZug(ZugEingabe letzterZug) {
    	this.letzterZug = letzterZug;
    	setChanged();
    	notifyObservers();
    }
}

package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import pd.SpielThreads;
import pd.zugsystem.ZugEingabe;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.serialisierung.SerialisierungsKontext;

public class Spiel extends Observable implements SerialisierungsKontext {
	private pd.Spiel spiel;

	private Brett brett;
	private Vector<Spieler> spieler;

	public EventQueue queue;
	public EndPunktInterface endpunkt;
	public Automat zugAutomat;

	public String spielerName;
	public Spieler spielerIch;
	public applikation.client.pd.Spieler aktuellerSpieler;

	private String hinweis;
	private ZugEingabe letzterZug;
	private IdentityHashMap<pd.spieler.Spieler, Spieler> spielerRegister;
	private Chat chat;

	public LinkedList<ZugEingabe> zugHistory;

	public Spiel() {
		spiel = new pd.Spiel();
		zugHistory = new LinkedList<ZugEingabe>();
		this.spielerRegister = new IdentityHashMap<pd.spieler.Spieler, Spieler>();

		spieler = new Vector<Spieler>();
		brett = new Brett(spiel.getBrett());
		chat = new Chat();
	}

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
			throw new RuntimeException("Kann app.Spieler für den Spieler "
			                           + spieler + " nicht finden!");
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

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, spiel);
	}
}

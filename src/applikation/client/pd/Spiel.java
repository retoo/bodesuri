package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import applikation.client.konfiguration.Konfiguration;

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
	public Konfiguration konfiguration;

	private String hinweis;
	private ZugEingabe letzterZug;
	private IdentityHashMap<pd.spieler.Spieler, Spieler> spielerRegister;
	public Chat chat;
	public LinkedList<ZugEingabe> zugHistory;
	private int zaehler = -1;

	public SteuerungsZustand steuerungsZustand;
	public Karte ausgewaehlteKarte;



	public Spiel(Konfiguration konfig) {
		this.konfiguration = konfig;

		spiel = new pd.Spiel();
		zugHistory = new LinkedList<ZugEingabe>();

		// Assoziiere PD-Spieler nach App-Spieler
		spielerRegister = new IdentityHashMap<pd.spieler.Spieler, Spieler>();
		spieler = new Vector<Spieler>();
		for (int i=0; i < spiel.getSpieler().size(); i++) {
			pd.spieler.Spieler pdSpieler = spiel.getSpieler().get(i);
			Spieler appSpieler = new Spieler( pdSpieler );
			spieler.add(appSpieler);
			spielerRegister.put(pdSpieler, appSpieler);
		}

		brett = new Brett(spiel.getBrett());
		chat = new Chat();
		steuerungsZustand = SteuerungsZustand.NICHTS;
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

	public int getZaehler() {
		return zaehler;
	}

	public void setZaehler(int i) {
		this.zaehler = i;
		setChanged();
		notifyObservers();
	}

	public ZugEingabe getLetzterZug() {
		return letzterZug;
	}

	public void setLetzterZug(ZugEingabe letzterZug) {
		this.letzterZug = letzterZug;
		setChanged();
		notifyObservers();
	}

	public SteuerungsZustand getSteuerungsZustand() {
		return steuerungsZustand;
	}

	public void setSteuerungsZustand(SteuerungsZustand steuerungsZustand) {
		this.steuerungsZustand = steuerungsZustand;
		setChanged();
		notifyObservers();
	}

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, spiel);
	}
}

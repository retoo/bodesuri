package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import pd.SpielThreads;
import pd.zugsystem.ZugEingabe;
import applikation.client.events.VerbindungAbgebrochenEvent;
import applikation.client.konfiguration.Konfiguration;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Dekoriert das Spiel aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie der Spieler der am Zug ist,
 * der Spieler bei dem der Client läuft, die ausgewählte Karte usw.
 * Verfügt ausserdem über eigene Spieler und ein eigenes Brett, die Spieler und
 * Brett aus der PD dekorieren.
 */
public class Spiel extends Observable implements SerialisierungsKontext {
	private pd.Spiel spiel;
	private Brett brett;
	private Vector<Spieler> spieler;

	public EventQueue queue;
	public EndPunktInterface endpunkt;
	public Automat zugAutomat;
	public Konfiguration konfiguration;

	public String spielerName;
	/** Der Spieler der den Client bedient */
	public Spieler spielerIch;
	/** Der Spieler der am Zug ist */
	public applikation.client.pd.Spieler aktuellerSpieler;
	/** Der Button welcher im Steuerungsview (rechts unten) angezeigt wird */
	public SteuerungsZustand steuerungsZustand;
	/** Der Inhalt des Hinweisfeldes in der Mittel des Spielbretts */
	private String hinweis;
	/** Alle bis jetzt gemachten Züge */
	private LinkedList<ZugEingabe> zugHistory;
	/** Die ausgewälte Karte */
	public Karte ausgewaehlteKarte;

	/** Abbildung von PD-Spielern zu Applikations-Spielern */
	private IdentityHashMap<pd.spieler.Spieler, Spieler> spielerRegister;
	public Chat chat;
	/** TODO: Philippe: Was ist das? -philippe */
	private int zaehler = -1;

	public Spiel(Konfiguration konfig) {
		this.konfiguration = konfig;

		spiel = new pd.Spiel();
		zugHistory = new LinkedList<ZugEingabe>();

		// Assoziiere PD-Spieler nach App-Spieler
		spielerRegister = new IdentityHashMap<pd.spieler.Spieler, Spieler>();
		spieler = new Vector<Spieler>();
		for (int i = 0; i < spiel.getSpieler().size(); i++) {
			pd.spieler.Spieler pdSpieler = spiel.getSpieler().get(i);
			Spieler appSpieler = new Spieler(pdSpieler);
			spieler.add(appSpieler);
			spielerRegister.put(pdSpieler, appSpieler);
		}

		brett = new Brett(spiel.getBrett());
		chat = new Chat();
		steuerungsZustand = SteuerungsZustand.NICHTS;
	}

	/**
	 * Sucht nach dem passenden {@link Spieler} zu einem
	 * {@link pd.spieler.Spieler}.
	 * 
	 * @param spieler
	 *            Der bekannte PD-Spieler
	 * @return Der gesuchte Applikations-Spieler
	 */
	public Spieler findeSpieler(pd.spieler.Spieler spieler) {
		Spieler s = spielerRegister.get(spieler);

		if (s == null) {
			throw new RuntimeException("Kann app.Spieler für den Spieler "
			                           + spieler + " nicht finden!");
		}
		return s;
	}

	/**
	 * Über den Endpunkt eine Nachricht an den Server senden. Dekoriert
	 * {@link dienste.netzwerk.EndPunktInterface#sende(Nachricht)}, löst aber
	 * im Fehlerfall einen {@link VerbindungAbgebrochenEvent} aus.
	 * 
	 * @param nachricht
	 *            Zu sendende Nachricht
	 */
	// TODO: Reto: Bitte schöner machen -Philippe
	public void sende(Nachricht nachricht) {
		try {
			endpunkt.sende(nachricht);
		} catch (VerbindungWegException e) {
			queue.enqueue(new VerbindungAbgebrochenEvent());
		}
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
		return zugHistory.getFirst();
	}

	public void setLetzterZug(ZugEingabe letzterZug) {
		zugHistory.addFirst(letzterZug);
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

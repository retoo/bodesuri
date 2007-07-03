package applikation.bot;

import java.util.IdentityHashMap;
import java.util.List;

import pd.regelsystem.ZugEingabe;
import pd.regelsystem.karten.Karte;
import applikation.client.controller.Controller;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.konfiguration.Konfiguration;
import applikation.client.pd.Chat;
import applikation.client.pd.Karten;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import dienste.eventqueue.EventQueue;

/**
 * Der BotController leitet die Ausgabe an den GUIController weiter und nimmt 
 * als Eingabe-Daten die Outputs des Bots um einen Computer-Spieler zu simulieren.
 */
public class BotController extends Controller {
	private Spiel spiel;
	private Controller gui;
	private Konfiguration konfig;
	private Bot bot;

	/**
	 * Erstellt einen neuen BotController.
	 * 
	 * @param konfig
	 * 			Defaultkonfiguration.
	 * @param queue
	 * 			Ereignis-Warteschlange des Automaten.
	 * @param guiController
	 * 			GUIController.
	 * @param bot
	 * 			Bot.
	 */
	public BotController(Konfiguration konfig, EventQueue queue, Controller guiController, Bot bot) {
		this.gui = guiController;
		this.eventQueue = queue;
		this.konfig = konfig;
		this.bot = bot;
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		throw new RuntimeException(fehlermeldung);
	}

	public void zeigeMeldung(String meldung) {
		if (gui != null)
			gui.zeigeMeldung(meldung);
	}

	public void zeigeLobby(List<Spieler> spieler, Chat chat) {
		if (gui != null)
			gui.zeigeLobby(spieler, chat);
	}

	public void zeigeSpiel(Spiel spiel) {
		if (gui != null)
			gui.zeigeSpiel(spiel);
		this.spiel = spiel;
	}

	public void zeigeVerbinden() {
		if (gui != null)
			gui.zeigeVerbinden();
		else
			verbinde(konfig.defaultHost, konfig.defaultPort, konfig.defaultName);
	}

	public void herunterfahren() {
		if (gui != null)
			gui.herunterfahren();
	}

	public void karteTauschenAuswaehlen() {
		Karten karten = spiel.spielerIch.getKarten();

		int el = (int) Math.floor(Math.random() * karten.size());

		this.karteAuswaehlen(karten.get(el));
		this.kartenTauschBestaetigen();
	}

	public void zugAufforderung() {
		List<ZugEingabe> moeglich = spiel.spielerIch.getMoeglicheZuege();

		try {
			Thread.sleep(1000); // "denken"
		} catch (InterruptedException e) {
		}

		IdentityHashMap<Karte, applikation.client.pd.Karte> karten = new IdentityHashMap<Karte, applikation.client.pd.Karte>();

		for (applikation.client.pd.Karte k : spiel.spielerIch.getKarten()) {
			karten.put(k.getKarte(), k);
		}

		ZugErfasstEvent ze = bot.macheZug(spiel, moeglich, karten);

		if (ze != null)
			eventQueue.enqueue(ze);
		else
			aufgeben();
	}

	public void zeigeJokerauswahl(boolean aktiv) {
	}

	public void verbindungsaufbauAbgebrochen() {
	}

	public void beenden() {
	}
}

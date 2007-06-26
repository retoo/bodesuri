package applikation.bot;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Vector;

import pd.karten.Karte;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.client.controller.Controller;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.konfiguration.Konfiguration;
import applikation.client.pd.Chat;
import applikation.client.pd.Karten;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import applikation.nachrichten.SpielerInfo;
import dienste.eventqueue.EventQueue;

public class BotController extends Controller {
	private Spiel spiel;
	private Controller gui;

	public BotController(Konfiguration konfig, EventQueue queue, Controller guiController, Bot bot) {
		this.gui = guiController;
		this.eventQueue = queue;
		/* TODO: Einbauen */

		konfig.debugAutoLogin = true;
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
	}

	public void karteTauschenAuswaehlen() {
		Karten karten = spiel.spielerIch.getKarten();

		int el = (int) Math.floor(Math.random() * karten.size());

		this.karteAuswaehlen(karten.get(el));
		this.kartenTauschBestaetigen();
	}

	public void zugAufforderung() {
		System.out.println("Berechne möglichkeiten");

		List<ZugEingabe> moeglich = spiel.spielerIch.getMoeglicheZuege();

		try {
			Thread.sleep(1000); // "denken"
		} catch (InterruptedException e) {
		}

		IdentityHashMap<Karte, applikation.client.pd.Karte> karten = new IdentityHashMap<Karte, applikation.client.pd.Karte>();

		for (applikation.client.pd.Karte k : spiel.spielerIch.getKarten()) {
			karten.put(k.getKarte(), k);
		}

		if (moeglich.isEmpty()) {
			System.out.println("nix möglich");
			this.aufgeben();
		} else {
			System.out.println("möglich sind: " + moeglich.size());

			int rand = (int) Math.floor(Math.random() * moeglich.size());
			ZugEingabe ze = moeglich.get(rand);
			List<Bewegung> bewegungen = ze.getBewegungen();
			applikation.client.pd.Karte karte = karten.get(ze.getKarte());

			/* Spezialfall Joker:
			 * Der Bot erhält mit getMoeglicheZüge() eines Jokers alle möglichen
			 * Züge, da der Joker alle möglichen Regeln verodert hat. Daraus
			 * kann er aber nicht rückschliessen welche konkrete Karte er
			 * spielen soll.
			 * Da der Server beim Validieren des Jokers aber eh alle Regeln
			 * akzeptiert, ist dies kein Problem.*/
			ZugErfasstEvent zee = new ZugErfasstEvent(spiel.spielerIch, karte, karte, bewegungen);
			eventQueue.enqueue(zee);
		}

	}

    public void zeigeSpielerInLobby(Vector<SpielerInfo> spielers) {
    }

    public void zeigeJokerauswahl(boolean aktiv) {
    }


}

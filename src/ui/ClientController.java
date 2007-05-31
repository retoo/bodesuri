package ui;

import java.util.Observable;
import java.util.Observer;

import applikation.client.BodesuriClient;
import applikation.client.zugAutomatAlt.zustaende.KarteWaehlen;
import applikation.client.zugAutomatAlt.zustaende.Ziehen;
import applikation.client.zustaende.AmZug;
import applikation.client.zustaende.Lobby;
import applikation.client.zustaende.LobbyStart;
import applikation.client.zustaende.NichtAmZug;
import applikation.client.zustaende.ProgrammStart;
import applikation.client.zustaende.SchwererFehler;
import applikation.client.zustaende.SpielStart;
import applikation.client.zustaende.VerbindungErfassen;
import applikation.client.zustaende.VerbindungSteht;
import dienste.automat.Automat;
import dienste.automat.zustaende.Zustand;

/**
 * Der ClientController dient zur Zustandsverwaltung des Clients. Er handhabt
 * die logischen Schritte, die in diesem Zustand ausgeführt werden.
 * Für die grafische Darstellung ist die <code>ui</code>-Schicht zuständig. Dort
 * sind ebenfalls Observer implementiert.
 *
 */
public class ClientController implements Observer {
	public ClientController() {

	}

	//TODO: Zügeln nach applikation.client.BodesuriClient
	/**
	 * Diese <code>main</code> Methode ist der Ersteinstiegspunkt der gesamten Applikation.
	 * Wenn ein Client gestartet wird, werden alle initialien Schritte hier eingeleitet.
	 *
	 * @param args
	 * @throws VerbindungWegException
	 */

	/**
     * OBSERVER-PATTERN: OBSERVER
     * überschreibt <code>update()</code> Methode des Observer.
     *
     * @param o Zu observierendes Objekt
     * @param arg Objekt
     */
	public void update(Observable o, Object arg) {
		Automat automat = (BodesuriClient) o;
		Zustand aktuellerZustand = automat.getAktuellerZustand();


		// TODO: Wäre schön, wenn das mit Polymorphismus ginge
		if (aktuellerZustand instanceof AmZug) {
			handle( (AmZug)aktuellerZustand );
		} else if (aktuellerZustand instanceof KarteWaehlen) {
			handle( (KarteWaehlen)aktuellerZustand );
		} else if (aktuellerZustand instanceof Lobby) {
			handle( (Lobby)aktuellerZustand );
		} else if (aktuellerZustand instanceof LobbyStart) {
			handle( (LobbyStart)aktuellerZustand );
		} else if (aktuellerZustand instanceof NichtAmZug) {
			handle( (NichtAmZug)aktuellerZustand );
		} else if (aktuellerZustand instanceof ProgrammStart) {
			handle( (ProgrammStart)aktuellerZustand );
		} else if (aktuellerZustand instanceof SchwererFehler) {
			handle( (SchwererFehler)aktuellerZustand );
		} else if (aktuellerZustand instanceof SpielStart) {
			handle( (SpielStart)aktuellerZustand );
		} else if (aktuellerZustand instanceof VerbindungErfassen) {
			handle( (VerbindungErfassen)aktuellerZustand );
		} else if (aktuellerZustand instanceof VerbindungSteht) {
			handle( (VerbindungSteht)aktuellerZustand );
		} else if (aktuellerZustand instanceof Ziehen) {
			handle( (Ziehen)aktuellerZustand );
		}

	}

	/**
	 * Definition wer am Zug ist.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(AmZug aktuellerZustand) {

	}

	/**
	 * Der Spieler kann eine Karte auswählen.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(KarteWaehlen aktuellerZustand) {

	}

	/**
	 * Der Spieler befindet sich in der Lobby.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(Lobby aktuellerZustand) {

	}

	/**
	 *
	 *
	 * @param aktuellerZustand
	 */
	public void handle(LobbyStart aktuellerZustand) {

	}

	/**
	 * Der Spieler ist nicht am Zug.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(NichtAmZug aktuellerZustand) {

	}

	/**
	 *
	 * @param aktuellerZustand
	 */
	public void handle(ProgrammStart aktuellerZustand) {

	}

	/**
	 * Ein schwerer Fehler ist aufgetreten. Eine entsprechende Darstellung des
	 * Ausnahmezustandes kann hier durchgeführt werden.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(SchwererFehler aktuellerZustand) {

	}

	/**
	 * Das spiel wurde gestartet.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(SpielStart aktuellerZustand) {

	}

	/**
	 * Das Spiel befindet sich im Zustand, in dem die Verbindungsdaten erfasst werden.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(VerbindungErfassen aktuellerZustand) {

	}

	/**
	 * Eine Verbindung wurde hergestellt. Der Client ist mit dem Server verbunden.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(VerbindungSteht aktuellerZustand) {

	}

	/**
	 * Es wird ein Zug erfasst und ausgeführt.
	 *
	 * @param aktuellerZustand
	 */
	public void handle(Ziehen aktuellerZustand) {

	}
}

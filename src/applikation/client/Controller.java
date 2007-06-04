package applikation.client;

import pd.Spiel;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Spieler;

public interface Controller {

	public abstract void starteZugerfassung();

	public abstract void verbinde(String host, int port_raw, String spieler);

	/**
	 * Programm wurde gestartet.
	 */
	public abstract void zeigeProgrammStart();

	/**
	 * Definition wer am Zug ist.
	 */
	public abstract void zeigeAktuellenZug();

	/**
	 * Der Spieler kann eine Karte auswählen.
	 */
	public abstract void zeigeKarteWahelen();

	/**
	 * Der Spieler befindet sich in der Lobby.
	 */
	public abstract void zeigeLobby();

	/**
	 * Zeigt die Lobby an.
	 */
	public abstract void zeigeLobbyStart();

	/**
	 * Der Spieler ist nicht am Zug.
	 */
	public abstract void zeigeNichtAmZug();

	/**
	 * Ein schwerer Fehler ist aufgetreten. Eine entsprechende Darstellung des
	 * Ausnahmezustandes kann hier durchgeführt werden.
	 */
	public abstract void zeigeSchwerenFehler();

	/**
	 * Das spiel wurde gestartet.
	 */
	public abstract void zeigeSpielStart();

	/**
	 * Das Spiel befindet sich im Zustand, in dem die Verbindungsdaten erfasst
	 * werden.
	 */
	public abstract void zeigeVerbindungErfassen();

	/**
	 * Eine Verbindung wurde hergestellt. Der Client ist mit dem Server
	 * verbunden.
	 */
	public abstract void zeigeVerbindungSteht();

	/**
	 * Es wird ein Zug erfasst und ausgeführt.
	 */
	public abstract void zeigeStartWaehlen();

	/**
	 * Reaktion auf Fehlermeldungen, die vom Automaten an den Controller
	 * gereicht werden.
	 *
	 * @param fehlermeldung
	 *            auszugebene Fehlermeldung
	 */
	public abstract void zeigeFehlermeldung(String fehlermeldung);

	public abstract void klickKarte(Karte geklickteKarte);

	public abstract void klickFeld(Feld geklicktesFeld);

	public abstract Spiel getSpiel();

	public abstract String getSpielerName();

	public abstract void setSpielerName(String spielerName);

	public abstract void setSpielerIch(Spieler spielerIch);

	public abstract boolean isZugAutomatControllerVorhanden();

}

package applikation.client.controller;

import java.util.List;

import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;

/**
 * Der Controller dient zum Kanalisieren der Zugriffe zwischen der UI- und
 * Applikationsschicht. Er bietet dem UI Methoden Ereignisse an den Automaten
 * weiterzuleiten und dem Automaten die Möglichkeit UI-Ereignisse unabhängig von
 * der Implementation des UIs auszulösen.
 */
public abstract class Controller {
	/**
	 * Die Verbindungsdaten erfragen.
	 *
	 * @param spielerName
	 */
	public abstract void zeigeVerbinden(String spielerName);

	/**
	 * Die Lobby anzeigen.
	 *
	 * @param spieler
	 *            Liste der Spieler
	 */
	public abstract void zeigeLobby(List<Spieler> spieler);

	/**
	 * Das Spielbrett anzeigen.
	 *
	 * @param spiel
	 * @param spielerIch
	 */
	public abstract void zeigeSpiel(Spiel spiel, Spieler spielerIch);

	/**
	 * Fehlermeldungen, die vom Automaten an den Controller gereicht werden,
	 * darstellen.
	 *
	 * @param fehlermeldung
	 *            anzuzeigende Fehlermeldung
	 */
	public abstract void zeigeFehlermeldung(String fehlermeldung);

	/**
	 * Hinweise, die vom Automaten an den Controller gereicht werden, darstellen
	 * um dem Spieler zu führen.
	 *
	 * @param hinweis
	 *            anzuzeigender Hinweis
	 */

	/**
	 * Die gespielte Karte, die vom Automaten an den Controller gereicht wird,
	 * darstellen.
	 *
	 * @param karte
	 *            gespielte Karte
	 */
//	 FIXME !!  public abstract void zeigeGespielteKarte(String karte);

	/**
	 * Die Auswahl von Karten (de-)aktivieren.
	 *
	 * param aktiv
	 */
	// FIXME RETO: public abstract void aktiviereKarte(Boolean aktiv);


}
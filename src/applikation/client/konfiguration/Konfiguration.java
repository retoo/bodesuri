package applikation.client.konfiguration;

/**
 * Standardwerte für die Anwendungskonfiguration.
 */
public class Konfiguration {
	/**
	 * Deubg-Option: Sollen Debug-Meldungen auf der Konsole ausgegben werden.
	 */
	public boolean debugMeldungen = false;

	/**
	 * Debug-Option: Ob man, obwohl noch Züge möglich sind, aufgeben darf.
	 */
	public boolean debugAufgabeImmerMoeglich = false;

	/**
	 * Debug-Option: Die VerbindenView überspringen und die Standardwerte
	 * verwenden.
	 */
	public boolean debugAutoLogin = false;

	/**
	 * Debug-Option: Keine Verzögerung beim Schliessen der Lobby.
	 */
	public boolean debugKeineLobbyVerzoegerung = false;

	/**
	 * Debug-Option: Keine Verzögerung beim Bot-Denken
	 */
	public boolean debugBotsZoegernNicht = false;

	/**
	 * Default-Hostname
	 */
	public String defaultHost = "localhost";

	/**
	 * Default-Port
	 */
	public int defaultPort = 7788;

	/**
	 * Default-Name
	 */
	public String defaultName = "Dog Spieler";
}

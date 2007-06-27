package applikation.client.konfiguration;

public class Konfiguration {
	/**
	 * Debug-Option: Sollen es immer möglich sein Aufzugeben
	 */
	public boolean debugAufgabeImmerMoeglich = false;

	/**
	 * Debug-Option: Automatischer Login mit den Defaultwerten
	 */
	public boolean debugAutoLogin = false;

	/**
	 * Lobby-Verzögerung sobald Spiel komplet ist
	 */
	public boolean debugKeineLobbyVerzoegerung = false;

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

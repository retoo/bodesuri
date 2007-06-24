package applikation.client.konfiguration;

public class DebugKonfiguration extends DefaultKonfiguration {
	/**
	 * Debug-Option: Sollen es immer möglich sein Aufzugeben
	 */
	public boolean debugAufgabeImmerMoeglich = true;

	/**
	 * Debug-Option: Automatischer Login mit den Default werden
	 */
	public boolean debugAutoLogin = true;

	/**
	 * Lobby-Verzögerung sobald Spiel komplett ist
	 */
	public boolean debugKeineLobbyVerzoegerung = true;
}

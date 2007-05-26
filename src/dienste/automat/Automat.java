package dienste.automat;

import java.util.IdentityHashMap;
import java.util.Map;

import dienste.automat.zustaende.AktiverZustand;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustandsautomat für alle Lebenslagen. Ermöglicht das saubere abarbeiten eines
 * Zustandsautomaten anhand eingehender Events.
 * 
 * Die Erstellung eines Automaten besteht aus folgenden Schritten:
 *  - Situationsabhänige aktive Zustandsklasse erstellen
 *  - Situationsabhänige passive Zustandsklasse erstellen
 *  - Zustände erstellen die die beiden obigen Klassen als Basis-Klasse
 * verwenden
 *  - Automat erstellen und alle Zustände mit
 * {@link Automat#registriere(Zustand)} registrieren
 *  - Startzustand mit {@link Automat#setStart(Class)} definieren
 *  - EvenQuelle implementieren. Zum Beispiel kann die EventQueue als 
 *    Back-End verwendet werden.
 *  - Automat mit run() starten
 *  
 *   FIXME: formatierugn
 * 
 */
public class Automat {
	private Zustand start;
	private EventQuelle eventQuelle;
	private Map<Class<? extends Zustand>, Zustand> zustaende;
	private Zustand aktuellerZustand;

	/**
	 * Erstellt einen neuen Automaten
	 */
	public Automat() {
		zustaende = new IdentityHashMap<Class<? extends Zustand>, Zustand>();

		registriere(new EndZustand());
	}

	/**
	 * Registriert einen neuen Zustand
	 * 
	 * @param zustand
	 *            hinzuzufügende Zustand
	 */
	public void registriere(Zustand zustand) {
		zustand.setAutomat(this);
		zustaende.put(zustand.getClass(), zustand);
	}

	/**
	 * Setzt den übergebenen Zustand als Startzustand
	 * 
	 * @param zustandsKlasse
	 *            Klasse des Zustandes der als Startzustand verwendet werden
	 *            soll
	 */
	public void setStart(Class<? extends Zustand> zustandsKlasse) {
		start = getZustand(zustandsKlasse);

		aktuellerZustand = start;
	}

	/**
	 * Setzt die zu verwendende EventQuelle
	 * 
	 * @param quelle
	 *            Zum lesen der Eventes verwendete Quelle
	 */
	public void setEventQuelle(EventQuelle quelle) {
		eventQuelle = quelle;
	}

	/**
	 * Startet den Mainloop
	 */
	public void run() {
		pruefeAutomat();

		while (true) {
			boolean cont = step();

			if (!cont) {
				return;
			}
		}
	}

	public boolean step() {
		Zustand neuerZustand;

		System.out.println("Uebergang nach: " + aktuellerZustand);

		aktuellerZustand.init();

		if (aktuellerZustand instanceof AktiverZustand) {
			AktiverZustand zustand = (AktiverZustand) aktuellerZustand;

			Event event = eventQuelle.getEvent();

			neuerZustand = zustand.handle(event);
		} else if (aktuellerZustand instanceof PassiverZustand) {
			PassiverZustand zustand = (PassiverZustand) aktuellerZustand;
			neuerZustand = zustand.handle();
		} else { /* kann nur endzustand sein */
			System.out.println("Erreichte Endzustand");
			return false;
		}

		aktuellerZustand = neuerZustand;

		return true;
	}

	/**
	 * Prüft den Automaten auf Fehler
	 */
	private void pruefeAutomat() {
		if (zustaende.size() <= 1)
			throw new RuntimeException("Keine Zustände registriert");

		if (start == null)
			throw new RuntimeException("Kein Startzustand gesetzt");

		if (eventQuelle == null)
			throw new RuntimeException("Keine EventQuelle definiertt");
	}

	public Zustand getAktuellerZustand() {
		return aktuellerZustand;
	}

	/**
	 * Such die Instanz des Zustandes der der übergebener Klasse angehört und an
	 * diesen Automaten gebunden ist.
	 * 
	 * @param klasse
	 *            Klasse des gesuchten Zustandes
	 * @return Instanz eines Zustandes
	 */
	public Zustand getZustand(Class<? extends Zustand> klasse) {
		Zustand zustand = zustaende.get(klasse);

		if (zustand == null)
			throw new RuntimeException("Nichtregistierer Zustand " + klasse);

		return zustand;
	}
}

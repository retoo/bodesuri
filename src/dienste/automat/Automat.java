package dienste.automat;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Observable;

import dienste.automat.zustaende.AktiverZustand;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustandsautomat für alle Lebenslagen. Ermöglicht das saubere abarbeiten eines
 * Zustandsautomaten anhand eingehender Events.
 * 
 * Die Erstellung eines Automaten besteht aus folgenden Schritten:
 * <ul>
 * <il>Situationsabhänige aktive Zustandsklasse erstellen</li>
 * <il>Situationsabhänige passive Zustandsklasse erstellen</li>
 * <il>Zustände erstellen die die beiden obigen Klassen als Basis-Klasse
 * verwenden</li>
 * <il>Automat erstellen und alle Zustände mit
 * {@link Automat#registriere(Zustand)} registrieren</li>
 * <il>Startzustand mit {@link Automat#setStart(Class)} definieren</li>
 * <il>EvenQuelle implementieren. Zum Beispiel kann die EventQueue als Baack-End
 * verwendet werden.</li>
 * <il>Automat mit run() starten</li>
 * </ul>
 * 
 */
public class Automat extends Observable {
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
	 * Startet den Zustandsautomaten und führt ihn so lange bis ein
	 * {@link EndZustand} eintritt.
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

	/**
	 * Verarbeitet einen einzigen Zustand. Bei Aktiven Zuständen bedeutet dies,
	 * dass auf neue Events gewartetw wird. Passive hingegen werden direkt
	 * ausgeführt.
	 * 
	 * Endzustände beenden den Zustandsautomaten.
	 * 
	 * Methode sollte ausser für Testcases nicht direkt verwendet werden. Der
	 * Zustandsuatomat sollte stattdesssen mit {@link Automat#run} abgearbeitet
	 * werden
	 * 
	 * @see AktiverZustand
	 * @see PassiverZustand
	 * @see EndZustand
	 * 
	 * @return true wenn es sich beim verarbeiteten Zustand nicht um einen
	 *         Endzustand handelte.
	 */
	@SuppressWarnings("deprecation")
    public boolean step() {
		Zustand neuerZustand;

		System.out.println("Uebergang nach: " + aktuellerZustand);

		aktuellerZustand.entry();
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

		aktuellerZustand.exit();
		aktuellerZustand = neuerZustand;

		// Observer benachrichtigen
		setChanged();
		notifyObservers();
		
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

	/**
	 * Meldete den Zustand in welchem sich der Zustandsautomaten zurzeit
	 * befindet.
	 * 
	 * @return Aktueller Zustand
	 */
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
